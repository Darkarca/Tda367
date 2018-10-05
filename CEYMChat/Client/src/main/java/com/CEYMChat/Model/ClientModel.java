package com.CEYMChat.Model;
import com.CEYMChat.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * This class will contain most of the model for the client-side. The model will likely be composed of
 * more classes in the "com.CEYMChat.Model" package.
 *
 * The model should handle all the requests and replies with the server.
 */


public class ClientModel {

    Connection connection = new Connection(this);
    String user;

    ClientController controller;


    private static ClientModel modelInstance = new ClientModel();

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

    public static ClientModel getModelInstance(){ return modelInstance;}


    public void sendStringMessage(String toSend, String receiver) throws IOException {

        Message message = MessageFactory.createStringMessage(toSend, user, receiver);
        System.out.println(message.getSender() + ": " + message.getData().toString());
        //connection.messageOutStream.writeObject(message);
        connection.setMessageOut(message);
    }

    /**
     * Processes a received message to a displayable format for the client.
     */
    public String processMessage(Message m) {
        String processedMessage;
        processedMessage = m.getSender() + ": " + m.getData().toString();
        return processedMessage;
    }


    public void displayNewMessage(Message m){
        String toDisplay;
        toDisplay = processMessage(m);
        controller.displayNewMessage(toDisplay);
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

    public void setController(ClientController controller) {
        this.controller = controller;
    }
}
