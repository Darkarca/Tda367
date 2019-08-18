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
    public void notify(Message message) {

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
        notify(message);
    }

    public boolean messageIsOfStringType(Message message){
        MessageType msgType = MessageType.STRING;
        if(MessageType.valueOf(message.getType().getSimpleName().toUpperCase()).equals(msgType)) {
            return true;
        }
        return false;
    }
    public void login() {
        notify(MessageFactory.createCommandMessage(new Command(CommandName.SET_USER,username),uInfo));
    }

    public UserInfo getUInfo() {
        return uInfo;
    }

    public void setUInfo(UserInfo uInfo) {
    this.uInfo=uInfo;
    }

    public List<Message<String>> getReceivedMessages() {
        return receivedMessages;
    }

    public List<Message<String>> getSentMessages() {
        return sentMessages;
    }
}
