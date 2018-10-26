package com.CEYMChatServer.Model;

import com.CEYMChatLib.Message;
import com.CEYMChatLib.MessageFactory;
import com.CEYMChatServer.Services.IWriter;
import com.CEYMChatServer.Services.Writer;
import com.CEYMChatLib.UserDisplayInfo;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user connected to the server.
 */
public class User {

    private UserDisplayInfo uInfo;
    private IWriter writer;
    private Socket socket;
    private List<UserDisplayInfo> friendsInfo = new ArrayList();
    private boolean online;
    private List<User> friends = new ArrayList();


    /** Getters and setters */
    public void setuInfo(UserDisplayInfo uInfo) {
        this.uInfo = uInfo;
    }

    public UserDisplayInfo getUInfo() {
        return uInfo;
    }

    public Socket getSocket() {
        return socket;
    }

    public IWriter getWriter() {
        return writer;
    }

    public List<UserDisplayInfo> getFriendsInfo(){
        return friendsInfo;
    }

    public void initIO(Socket socket) {
        this.writer = new Writer(socket);
        this.socket = socket;
    }


    /**
     * Syncs the users list of registered friendsInfo with
     * a list received by its corresponding client
     */
    public void syncFriends(Message message){
        List<UserDisplayInfo> receivedList = (List<UserDisplayInfo>) message.getData();
        Boolean add = true;
        for(UserDisplayInfo uInfo : receivedList) {
            for(UserDisplayInfo friends: friendsInfo){
                if(uInfo.getUsername().equals(friends.getUsername())){
                    add = false;
                }
            }
            if(add){
                uInfo.setIsFriend(true);
                friendsInfo.add(uInfo);
            }
        }
    }


    /**
     * Merges this users friendsInfo with another list
     * of users so that they can both be sent to a client
     */
    public Message checkFriends(Message message) {
        List<UserDisplayInfo> listToSend = (List<UserDisplayInfo>) message.getData();
        for (UserDisplayInfo friends : friendsInfo) {
            Boolean add = true;
            for (UserDisplayInfo uInfo : listToSend) {
                if (uInfo.getUsername().equals(friends.getUsername())) {
                    add = false;
                }
            }
            if (add) {
                friends.setIsFriend(true);
                listToSend.add(friends);
            }
        }
        return MessageFactory.createFriendInfoList(listToSend, message.getSender(), message.getReceiver());
    }


    /**
     *  Writes a message to the outputStream so
     *  that the client can read it from their inputStream
     */
    public void sendMessage(Message message) {
        writer.setOutMessage(message);
    }

    public void addFriends(UserDisplayInfo uInfo) {
        friendsInfo.add(uInfo);
    }

    public void removeFriends(UserDisplayInfo toRemove) {
        for (UserDisplayInfo uInfo : friendsInfo) {
            if (uInfo.getIsFriend() && toRemove.getUsername() == uInfo.getUsername()) {
                friendsInfo.remove(uInfo);
                return;
            }
        }
    }


    public boolean isOnline() {
       return this.online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void addFriend(User toBeAdded) {
        friends.add(toBeAdded);
        System.out.println("Friend added" + toBeAdded.getUInfo().getUsername());
    }
}
