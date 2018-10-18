package com.CEYMChat.Model;
import com.CEYMChat.*;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Model for the client
 */
public class ClientModel {

    private Socket socket;
    private String username;
    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();
    //private static ClientModel modelInstance = new ClientModel();


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    //public static ClientModel getModelInstance(){ return modelInstance;}

    public void setFriendList(ArrayList<UserDisplayInfo> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<UserDisplayInfo> getFriendList() {
        return friendList;
    }

    public void setUsername(String user){
        this.username = user;
    }

    public String getUsername(){
        return username;
    }

}
