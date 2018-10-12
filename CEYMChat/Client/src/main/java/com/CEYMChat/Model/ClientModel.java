package com.CEYMChat.Model;
import com.CEYMChat.*;
import com.CEYMChat.Services.Connection;
import com.CEYMChat.Services.IService;

import java.io.IOException;
import java.util.ArrayList;


public class ClientModel {

    private IService connection = new Connection(this);
    private String username;
    private ArrayList<UserDisplayInfo> friendList;
    private ClientController controller;
    private boolean loggedIn = false;


    private static ClientModel modelInstance = new ClientModel();
    public void connectToServer (ClientController c){
        connection.start(c);
        System.out.println("Connection started");
    }
    public void login(){
        loggedIn = true;
    }
    public void logout(){
        loggedIn = false;
    }
    public boolean getLoginStatus(){
        return loggedIn;
    }

    public static ClientModel getModelInstance(){ return modelInstance;}

    public void setFriendList(ArrayList<UserDisplayInfo> friendList) {
        this.friendList = friendList;
    }

    public void setUsername(String user){
        this.username = user;
    }

    public String getUsername(){
        return username;
    }

    public IService getConnectionService(){
        return connection;
    }
}
