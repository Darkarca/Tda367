package com.CEYMChat.Model;

/**
 * This class will contain most of the model for the client-side. The model will likely be composed of
 * more classes in the "com.CEYMChat.Model" package.
 *
 * The model should handle all the requests and replies with the server. (I think?)
 */


public class ClientModel {


    private static final ClientModel modelInstance = new ClientModel();
    /**
     * Private constructor with getModelInstance()
     * to ensure only one model is ever created (Singleton pattern)
     * **/
    private ClientModel(){}

    public static ClientModel getModelInstance(){return modelInstance;}




}
