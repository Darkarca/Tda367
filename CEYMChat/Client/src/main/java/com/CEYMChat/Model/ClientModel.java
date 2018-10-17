package com.CEYMChat.Model;
import com.CEYMChat.*;
import com.CEYMChat.Services.Connection;
import com.CEYMChat.Services.IService;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Model for the client
 */

public class ClientModel {

    private Socket socket;
    private IService connection;
    private String username;
    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();
    private static ClientModel modelInstance = new ClientModel();

    /**
     * Start the connection service
     * @param c Passes a controller that controls the connection service.
     */
    public void connectToServer (ClientController c, ClientModel clientModel){
        modelInstance = clientModel;
        connection = new Connection(clientModel, c);
        connection.start();
        System.out.println("Connection started");
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public static ClientModel getModelInstance(){ return modelInstance;}

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

    public IService getConnectionService(){
        return connection;
    }

}
