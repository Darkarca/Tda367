package com.CEYMChatClient.Model;

import com.CEYMChatLib.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Model for the client */
public class ClientModel implements IObserveable {

    private List<IObserver> observerList = new ArrayList<>();
    private String username;
    private List<UserDisplayInfo> userList = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Message> sentMessages = new ArrayList<>();
    private File selectedFile;
    private List<String> mutedFriends = new ArrayList<>();
    private List<UserDisplayInfo> blockedFriends = new ArrayList<>();
    private List<UserDisplayInfo> friendList = new ArrayList<>();

    /** Getters, setters and adders **/
    public void setUserList(List<UserDisplayInfo> userList) {
        this.userList = userList;
    }
    public void setUsername(String user){
        this.username = user;
    }
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }
    public void addFriends(UserDisplayInfo uInfo){
        if (uInfo.getIsFriend() && !friendList.contains(uInfo)){
            friendList.add(uInfo);
        }
    }
    public void addBlockedFriend(UserDisplayInfo item) {
        blockedFriends.add(item);
    }
    public void addMuted(String friendUsername) {
        mutedFriends.add(friendUsername);
    }
    public void addSentMessage (Message message){
        sentMessages.add(message);
    }
    public void addReceivedMessage(Message message){
        receivedMessages.add(message);
    }
    public void removeFriends(UserDisplayInfo uInfo){
        if(!uInfo.getIsFriend() && friendList.contains(uInfo)){
            friendList.remove(uInfo);
        }
    }
    public void removeMuted(String text) {
        mutedFriends.remove(text);
    }
    public boolean isMuted(String userName ) {
        for (String s : getMutedFriends()) {
            if (s.equals(userName)){
                return true;
            }
        }
        return false;
    }
    public boolean isBlocked(UserDisplayInfo uInfo) {
        for (UserDisplayInfo blocked : getBlockedFriends()) {
            if (blocked.getUsername().equals(uInfo.getUsername())) {
                return true;
            }
        }
        return false;
    }
    public String getUsername(){
        return username;
    }
    public List<UserDisplayInfo> getUserList() {
        return userList;
    }
    public List<UserDisplayInfo> getFriendList() {
        return friendList;
    }
    private List<UserDisplayInfo> getBlockedFriends() {
        return this.blockedFriends;
    }
    private List<String> getMutedFriends() {
        return this.mutedFriends;
    }
    public File getSelectedFile() {
        return selectedFile;
    }

    /** Saves all sent and received messages into a file
     * @param list The list of messages to be saved
     * @param filename The location to save the file to
     */
    public void saveArrayListToFile(List<Message> list, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(Message message: list) {
            if(message.getSender().equals(username)) {
                writer.write("Me: " + "," + message.getData().toString() + ",");
            }
            else{
                writer.write(message.getSender() + "," + message.getData().toString() + ",");
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
    public void displayNewMessage(Message message) {
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
        displayNewMessage(message);
    }

    public void login() {
        displayNewMessage(MessageFactory.createCommandMessage(new Command(CommandName.SET_USER,username),username));
    }
}
