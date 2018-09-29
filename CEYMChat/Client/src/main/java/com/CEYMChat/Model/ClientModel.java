package com.CEYMChat.Model;
import com.CEYMChat.Connection;
import com.CEYMChat.Message;
import com.CEYMChat.MessageFactory;
import com.CEYMChat.User;

/**
 * This class will contain most of the model for the client-side. The model will likely be composed of
 * more classes in the "com.CEYMChat.Model" package.
 *
 * The model should handle all the requests and replies with the server.
 */


public class ClientModel {

    User currentUser;
    Connection connection = new Connection();


    private static final ClientModel modelInstance = new ClientModel();
    /**
     * Private constructor with getModelInstance()
     * to ensure only one model is ever created (Singleton pattern)
     * **/
    private ClientModel(){
        connection.start();
    }

    public static ClientModel getModelInstance(){return modelInstance;}

    public void sendStringMessage(String s){
        Message message = MessageFactory.createStringMessage(s);

    }

    /**
     * Processes a received message
     * (Not yet implemented)
     */
    public void processMessage(Message m){

        }

}
