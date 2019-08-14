package com.CEYMChatClient.Model;

import com.CEYMChatLib.*;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Model for the client */
public class ClientModel implements IObserveable {

    private List<IObserver> observerList = new ArrayList<>();
    private List<UserInfo> blockedFriends = new ArrayList<>();
    private List<UserInfo> friendList = new ArrayList<>();
    private List<UserInfo> userList = new ArrayList<>();
    private List<Message<String>> receivedMessages = new ArrayList<>();
    private List<Message<String>> sentMessages = new ArrayList<>();
    private List<UserInfo> mutedFriends = new ArrayList<>();
    private String username;
    private UserInfo uInfo;
    private File selectedFile;

    /** Getters, setters and adders **/
    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }
    public void setUsername(String user){
        this.username = user;
    }
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }
    public void addFriends(UserInfo uInfo){
        if (uInfo.getIsFriend() && !friendList.contains(uInfo)){
            friendList.add(uInfo);
        }
    }
    public void addBlockedFriend(UserInfo item) {
        blockedFriends.add(item);
    }
    public void addMuted(UserInfo uInfo) {
        mutedFriends.add(uInfo);
    }
    public void addSentMessage (Message message){
        if(messageIsOfStringType(message)) {
            sentMessages.add(message);
        }
    }
    public void addReceivedMessage(Message message){
        if(messageIsOfStringType(message)) {
            receivedMessages.add(message);
        }
    }
    public void removeFriends(UserInfo uInfo){
        if(!uInfo.getIsFriend() && friendList.contains(uInfo)){
            friendList.remove(uInfo);
        }
    }
    public void removeMuted(String text) {
        mutedFriends.remove(text);
    }
    public boolean isMuted(UserInfo mutedUser) {
        for (UserInfo uInfo : getMutedFriends()) {
            if (uInfo.equals(mutedUser)){
                return true;
            }
        }
        return false;
    }
    public boolean isBlocked(UserInfo uInfo) {
        for (UserInfo blocked : getBlockedFriends()) {
            if (blocked.getUsername().equals(uInfo.getUsername())) {
                return true;
            }
        }
        return false;
    }
    public List<UserInfo> getUserList() {
        return userList;
    }
    public List<UserInfo> getFriendList() {
        return friendList;
    }
    private List<UserInfo> getBlockedFriends() {
        return this.blockedFriends;
    }
    private List<UserInfo> getMutedFriends() {
        return this.mutedFriends;
    }
    public String getUsername(){
        return username;
    }
    public File getSelectedFile() {
        return selectedFile;
    }

    /** Saves all sent and received messages into a file
     * @param list The list of messages to be saved
     * @param filename The location to save the file to
     */
    public void saveArrayListToFile(List<Message<String>> list, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(Message message: list) {
            if(message.getSender().getUsername().equals(username)) {
                writer.write("Me: " + "," + message.getData().toString() + ",");
            }
            else{
                writer.write(message.getSender().getUsername() + ": ," + message.getData().toString() + ",");
            }
        }
        writer.close();
    }

    /** Calls saveArrayListToFile to save all Received messages
     * @param filename the location to save the file to
     */
    public void saveReceivedMessages(String filename) {
        try {
            saveArrayListToFile(receivedMessages, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Calls saveArrayListToFile to save all sent messages
     * @param filename the location to save the file to
     */
    public void saveSendMessages(String filename) {
        try {
            saveArrayListToFile(sentMessages, filename);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File save error");
            alert.setHeaderText("File save error");
            alert.setContentText("Your files could not be saved.");

            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /** Loads messages that were saved during the last session
     * @param filename the location to load messages from
     */
    public List<String> loadSavedMessages(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = "";
        String cvsSplitBy = ",";
        String [] savedMessages = {};
        while((line = reader.readLine())!=null){
            savedMessages = line.split(cvsSplitBy);
        }
        return new ArrayList<String>(Arrays.asList(savedMessages));
    }

    /** Combines two saved lists of messages in one list */
    public void combineSavedLists (List<String> savedSentMessages, List<String> savedReceivedMessages,List<String>allSavedMessages){
        int min = Math.min(savedReceivedMessages.size(),savedSentMessages.size());
        int index = 0;
        int tmp = 0;
        for (int i = 0; i < min/2; i++){
            allSavedMessages.add(savedSentMessages.get(tmp));
            allSavedMessages.add(savedSentMessages.get(tmp + 1));
            allSavedMessages.add(savedReceivedMessages.get(tmp));
            allSavedMessages.add(savedReceivedMessages.get(tmp + 1));
            tmp = i + 2;
            index = i;
        }
        index = index * 4;
        if (min == savedSentMessages.size()){
            addElementsAfterIndex(savedReceivedMessages,allSavedMessages,index);
        }
        else if (min == savedReceivedMessages.size()) {
            addElementsAfterIndex(savedSentMessages,allSavedMessages,index);

        }
    }

    /**
     * Saves messages locally so that they can
     * be loaded the next time you load the client
     */
    public void saveMessages() {
        saveReceivedMessages("Client/messages/received.csv");
        saveSendMessages("Client/messages/sent.csv");
    }

    /** adds elements of a list to another list and begin from a given index */
    private void addElementsAfterIndex(List<String> savedList,List<String>allSavedMessages,int index){
        for (int i = index; i < savedList.size(); i++){
            allSavedMessages.add(savedList.get(i));
        }
    }

    /**
     * Disconnects all observers when the connection is ended.
     */
    public void connectionEnded() {
        for (IObserver client: observerList) {
            client.disconnect();
        }
    }

    /**
     * Tells observers to update when a new message been received.
     * @param message
     */
    public void update(Message message) {

        for (IObserver observer: observerList) {
            observer.update(message);
        }
    }

    /**
     * Registers an observer to the Observerlist
     * @param observer
     */
    @Override
    public void register(IObserver observer) {
        observerList.add(observer);
        System.out.println(observer.toString());
    }

    @Override
    public void unregister(IObserver observer) {
        observerList.remove(observer);
    }

    public void addMessage(Message message) {
        addSentMessage(message);
        update(message);
    }
    public boolean messageIsOfStringType(Message message){
        MessageType msgType = MessageType.STRING;
        if(MessageType.valueOf(message.getType().getSimpleName().toUpperCase()).equals(msgType)) {
            return true;
        }
        return false;
    }

    public void login() {
        update(MessageFactory.createCommandMessage(new Command(CommandName.SET_USER,username),uInfo));
    }

    public UserInfo getUInfo() {
        return uInfo;
    }

    public void setUInfo(UserInfo uInfo) {
    this.uInfo=uInfo;
    }
}
