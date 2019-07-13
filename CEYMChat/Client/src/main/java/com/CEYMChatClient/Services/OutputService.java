package com.CEYMChatClient.Services;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.IObserver;
import com.CEYMChatLib.*;
import java.io.*;
import java.net.Socket;

public class OutputService implements IOutput, IObserver {
    private ClientModel model;
    private ObjectOutputStream messageOutStream;
    Socket socket;

    OutputService(ClientModel model, Socket socket)
    {
            this.socket=socket;
            this.model=model;
            model.register(this);
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
     */
    public void connectToServer(){
        try {
            messageOutStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to open an OutStream from socket, exiting hard");
            System.exit(1);
            e.printStackTrace();
        }
    }

    /**
     * Informs the Server that a user has connected
     * so that the Server can identify the user
     */
    public void login() {
        try {
            sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.SET_USER,model.getUInfo().getUsername()),model.getUInfo()));
        } catch (IOException e) {
            System.out.println("Failed to login, exiting hard");
            System.exit(1);
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
                File toSend = new File (model.getSelectedFile().getPath());
                byte [] toSendArray  = new byte [(int)toSend.length()];
                FileInputStream inputStream = new FileInputStream(toSend);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bufferedInputStream.read(toSendArray,0,toSendArray.length);

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(toSendArray,0,toSendArray.length);
                outputStream.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Sleep interrupted");
                    e.printStackTrace();
                }
                break;
        }
    }


    /**
     * Called when model gets updated
     * @param message
     */
    @Override
    public void update(Message message) {
        if(message.getSender() != null && message.getSender().getUsername().equals(model.getUsername())){
            try {
                sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Safely stops all connections
     */
    @Override
    public void disconnect() {
        try {
            messageOutStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Failed to disconnect safely, exiting hard");
            System.exit(1);
            e.printStackTrace();
        }
    }
}
