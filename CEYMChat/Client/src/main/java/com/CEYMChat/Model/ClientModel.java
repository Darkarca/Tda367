package com.CEYMChat.Model;
import com.CEYMChat.Command;
import com.CEYMChat.Message;
import com.CEYMChat.MessageFactory;

import java.io.IOException;

/**
 * This class will contain most of the model for the client-side. The model will likely be composed of
 * more classes in the "com.CEYMChat.Model" package.
 *
 * The model should handle all the requests and replies with the server.
 */


public class ClientModel {

    Connection connection = new Connection();
    String user;

    private static final ClientModel modelInstance = new ClientModel();

    /**
     * Private constructor with getModelInstance()
     * to ensure only one model is ever created (Singleton pattern)
     * **/
    private ClientModel(){

    }


    public void connectToServer (){
        connection.start();
        System.out.println("Connection started");
    }

    public static ClientModel getModelInstance(){return modelInstance;}

    public void sendStringMessage(String s) throws IOException {

        Message message = MessageFactory.createStringMessage(s, user);
        System.out.println(message.getSender() + ": " + message.getData().toString());
        //connection.messageOutStream.writeObject(message);
        connection.setMessageOut(message);
    }

    /**
     * Processes a received message
     * (Not yet implemented)
     */
    public void processMessage(Message m){

        }

        public void setUser(String user){
        this.user = user;
        }

    public String retrieveMessage() throws IOException, ClassNotFoundException {
        Message m = (Message) connection.messageInStream.readObject();
        String s = m.getSender() + ": " + m.getData().toString();
        return s;
    }


    public void sendCommandMessage(String sCommand, String sData) throws IOException {
        Message message = MessageFactory.createCommandMessage(new Command(sCommand, sData), user);
        System.out.println("Command sent: " + sCommand + " with data: " + sData);
        connection.setMessageOut(message);
    }
    public String getUser(){
        return user;
    }
}
