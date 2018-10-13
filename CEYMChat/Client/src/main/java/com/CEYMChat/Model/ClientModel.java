package com.CEYMChat.Model;
import com.CEYMChat.*;
import com.CEYMChat.Services.Connection;
import com.CEYMChat.Services.IService;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;



public class ClientModel {

   // private IService connection = new Connection(this);
    private Socket socket;
    private String username;
    private boolean loggedIn = false;
    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();

    public ClientModel(){
        try {
            this.socket = new Socket("localhost", 9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //private ClientController controller;
    //private boolean loggedIn = false;
    //private static ClientModel modelInstance = new ClientModel();


    public Socket getSocket() {
        return socket;
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

   // public static ClientModel getModelInstance(){ return modelInstance;}

    public void setFriendList(ArrayList<UserDisplayInfo> friendList) {
        this.friendList = friendList;
    }


    //public void login(){loggedIn = true;}

   // public void logout()loggedIn = false;}

   // public boolean getLoginStatus(){ return loggedIn; }

    public void createFriendList(ArrayList<UserDisplayInfo> friendList) {
        this.friendList.clear();
        for (UserDisplayInfo uInfo : friendList){
            this.friendList.add(uInfo);
        }
    }

    public ArrayList<UserDisplayInfo> getfriendList() {
        return friendList;
    }

    public void setUsername(String user){
        this.username = user;
    }

    public String getUsername(){
        return username;
    }

}
