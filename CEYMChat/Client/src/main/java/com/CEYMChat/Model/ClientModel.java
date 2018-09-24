package com.CEYMChat.Model;



public class ClientModel {


    private static final ClientModel modelInstance = new ClientModel();
    /**
     * Private constructor with getModelInstance()
     * to ensure only one model is ever created (Singleton pattern)
     * **/
    private ClientModel(){}

    public static ClientModel getModelInstance(){return modelInstance;}




}
