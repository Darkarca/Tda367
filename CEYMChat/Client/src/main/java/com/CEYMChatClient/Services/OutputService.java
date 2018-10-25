package com.CEYMChatClient.Services;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.*;
import java.io.*;
import java.net.Socket;

public class OutputService implements IOutput{
    private ClientModel model;
    private ObjectOutputStream messageOutStream;
    Socket socket;

    public OutputService(ClientModel model)
    {
            this.model=model;
    }


    /** Getters and setters */
    private void setMessageOut(Message messageOut) throws IOException {
        messageOutStream.writeObject(messageOut);
        System.out.println("Message sent: " + messageOut.getData());
    }

    public Socket getSocket(){
        return socket;
    }

    /**
     * Connect client to the server
     * @param serverIP The IP to connect to
     */
    public void connectToServer(String serverIP){
        try {
            socket = new Socket(serverIP, 9000);
            messageOutStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Informs the Server that a user has connected
     * so that the Server can identify the user
     * @param userName  the username of the user who sends the command
     */
    public void login(String userName) {
        try {
            sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.SET_USER,userName),userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    /**
     * Decides how messages are sent to the Server
     * @param message the sent message
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {
        MessageType msgType = MessageType.valueOf(message.getType().getSimpleName().toUpperCase());
        switch(msgType){
            case STRING: setMessageOut(message);  // Messages containing Strings are sent to the outputStream
                    // Changes the models state so that the message can be saved to the device later
                break;
            case COMMAND: setMessageOut(message); // Commands are simply sent to the Server to let the Server perform said command
                break;
            case ARRAYLIST: {
                setMessageOut(message);           // Sends an updated list of friends to the Server so that the Servers state can be updated
                break;
            }
            case MESSAGEFILE: setMessageOut(message);    // Messages containing a FILE will be sent via the message object but arrives corrupt at the Server
                FileInputStream fileInput = new FileInputStream(model.getSelectedFile());   // Sending a FILE is instead done by sending an array of bytes
                BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);     // To a separate inputStream
                bufferedInput.read(((MessageFile)(message.getData())).getByteArray(),0,(((MessageFile)(message.getData())).getByteArray().length));
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(((MessageFile)(message.getData())).getByteArray(),0,((MessageFile)(message.getData())).getByteArray().length);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //outputStream.flush();
                break;
        }
    }


    /**
     * Safely stops all connections
     */
    public void stop(){
        try {
            messageOutStream.close();
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
