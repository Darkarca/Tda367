package com.CEYMChat.Model;
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

    //User currentUser;
   Connection connection = new Connection();


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
        Message message = MessageFactory.createStringMessage(s);
        System.out.println(message.getData().toString());
        //connection.messageOutStream.writeObject(message);
        connection.setMessageOut(message);
    }

    /**
     * Processes a received message
     * (Not yet implemented)
     */
    public void processMessage(Message m){

        }

}
