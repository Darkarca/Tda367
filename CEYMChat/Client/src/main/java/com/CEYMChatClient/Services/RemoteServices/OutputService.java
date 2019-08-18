package com.CEYMChatClient.Services.RemoteServices;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.IMessageObserver;
import com.CEYMChatLib.*;
import java.io.*;
import java.net.Socket;

public class OutputService implements IOutput, IMessageObserver {
    private ClientModel model;
    private ObjectOutput messageOutStream;
    private Socket socket;

    OutputService(ClientModel model, Socket socket) {
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
            case MESSAGEFILE: setMessageOut(message);    // Messages containing a FILE will be sent
                sendFile();
                break;
                default: setMessageOut(message);
        }
    }

    /**
     * Called when model gets updated
     * @param message
     */
    @Override
    public void updateNewMessage(Message message) {
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

    private void sendFile() throws IOException {
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
    }
}
