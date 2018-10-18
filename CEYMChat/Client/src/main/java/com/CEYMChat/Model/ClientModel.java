package com.CEYMChat.Model;
import com.CEYMChat.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Model for the client
 */
public class ClientModel {

    private Socket socket;
    private String username;
    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();
    private ArrayList<Message> receivedMessages = new ArrayList<>();
    private ArrayList<Message> sentMessages = new ArrayList<>();
    private File selectedFile;

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
            if(m.getSender().equals(username)) {
                writer.write("Me: " + "," + m.getData().toString() + ",");
            }
            else{
                writer.write(m.getSender() + "," + m.getData().toString() + ",");
            }
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

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public ArrayList<String> loadSavedSentMessage() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Client/messages/sent.csv"));
        String line = "";
        String cvsSplitBy = ",";
        String [] savedMessages = {};
        while((line = br.readLine())!=null){
            savedMessages = line.split(cvsSplitBy);

        }
        ArrayList<String> savedMessagesList = new ArrayList<String>(Arrays.asList(savedMessages));
        System.out.println(savedMessagesList.toString());
        return savedMessagesList;


    }

    public ArrayList<String> loadSavedReceivedMessage() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Client/messages/received.csv"));
        String line = "";
        String cvsSplitBy = ",";
        String [] savedMessages = {};
        while((line = br.readLine())!=null){
            savedMessages = line.split(cvsSplitBy);

        }
        ArrayList<String> savedMessagesList = new ArrayList<String>(Arrays.asList(savedMessages));
        System.out.println(savedMessagesList.toString());
        return savedMessagesList;


    }
}
