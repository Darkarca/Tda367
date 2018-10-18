package com.CEYMChat.Model;
import com.CEYMChat.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Model for the client
 */
public class ClientModel {

    private Socket socket;
    private String username;
    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();
    private ArrayList<Message> receivedMessages = new ArrayList<>();
    private ArrayList<Message> sentMessages = new ArrayList<>();

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
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
    public void addReceivedMessage(Message m){
        receivedMessages.add(m);
    }
    public void addSentMessage (Message m){
        sentMessages.add(m);
    }
    public void saveArrayListToFile(ArrayList<Message> list, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(Message m: list) {
            writer.write(m.getSender()+","+m.getData().toString()+",");
        }
        writer.close();
    }

    public void saveReceivedMessages() {
        try {
            saveArrayListToFile(receivedMessages, "Client/messages/received.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSendMessages() {
        try {
            saveArrayListToFile(sentMessages, "Client/messages/sent.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
