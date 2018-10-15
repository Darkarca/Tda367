package com.CEYMChat.Model;
import com.CEYMChat.*;
import com.CEYMChat.Services.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Model for the client
 */

public class ClientModel {
    Socket socket;
    ObjectInputStream objectIn;
    ObjectOutputStream objectOut;

    public ClientModel(Socket socket,ObjectInputStream objectIn,ObjectOutputStream objectOut ){
        this.socket = socket;
        this.objectIn = objectIn;
        this.objectOut = objectOut;
    }

    private String username;
    private boolean loggedIn = false;

    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();
    //private ClientController controller;
   // private boolean loggedIn = false;

   // private static ClientModel modelInstance = new ClientModel();

    /**
     * Start the connection service
     * @param c Passes a controller that controls the connection service.
     */
    public void setUpConnection() {


        try {
            socket = new Socket("localhost", 9000);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread started");
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

    //public static ClientModel getModelInstance(){ return modelInstance;}

    public void setFriendList(ArrayList<UserDisplayInfo> friendList) {
        this.friendList = friendList;
    }


    //public void login(){loggedIn = true;}

   // public void logout()loggedIn = false;}

   // public boolean getLoginStatus(){ return loggedIn; }

    /**
     * Adds other users to the friendlist Arraylist
     * @param friendList
     */
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
