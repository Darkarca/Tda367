package com.CEYMChatClient.Model;

import com.CEYMChatLib.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/** Model for the client */
public class ClientModel {

    private Socket socket;
    private String username;
    private ArrayList<UserDisplayInfo> userList = new ArrayList<>();
    private ArrayList<UserDisplayInfo> friendList = new ArrayList<>();
    private ArrayList<Message> receivedMessages = new ArrayList<>();
    private ArrayList<Message> sentMessages = new ArrayList<>();
    private File selectedFile;


    private String serverIP;

    /** Getters, setters and adders **/
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public void setUserList(ArrayList<UserDisplayInfo> userList) {
        this.userList = userList;
    }
    public ArrayList<UserDisplayInfo> getUserList() {
        return userList;
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
    public File getSelectedFile() {
        return selectedFile;
    }
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    /** Saves all sent and received messages into a file */
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

    /** Calls saveArrayListToFile to save all Received messages */
    public void saveReceivedMessages(String filename) {
        try {
            saveArrayListToFile(receivedMessages, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Calls saveArrayListToFile to save all sent messages */
    public void saveSendMessages(String filename) {
        try {
            saveArrayListToFile(sentMessages, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Loads messages that were saved during the last session */
    public ArrayList<String> loadSavedMessages(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
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

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerIP() {
        return serverIP;
    }
}
