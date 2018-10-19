package com.CEYMChat.Model;

import com.CEYMChat.Message;
import com.CEYMChat.MessageFactory;
import com.CEYMChat.Services.IWriter;
import com.CEYMChat.Services.Writer;
import com.CEYMChat.UserDisplayInfo;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private IWriter writer;
    private List<UserDisplayInfo> friends = new ArrayList();

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void initIO(Socket socket, ServerModel model) {
        this.writer = new Writer(socket);
    }

    public void syncFriends(Message m){     // Syncs the users list of registered friends with a list received by its corresponding client
        List<UserDisplayInfo> receivedList = ((List<UserDisplayInfo>)(m.getData()));
        Boolean add = true;
        for(UserDisplayInfo uInfo : receivedList) {
            for(UserDisplayInfo friends:friends){
                if(uInfo.getUsername().equals(friends.getUsername())){
                    add = false;
                }
            }
            if(add){
                uInfo.setIsFriend(true);
                friends.add(uInfo);
            }
        }
    }

    public Message checkFriends(Message m) {
        List<UserDisplayInfo> listToSend = ((List<UserDisplayInfo>) (m.getData()));
        for (UserDisplayInfo friends : friends) {
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
        return MessageFactory.createFriendInfoList(listToSend, m.getSender(), m.getReceiver());
    }
    public void sendMessage(Message m) {
        writer.setOutMessage(m);
    }

    public void sendInfo(Message m) {
        writer.setOutMessage(m);
    }

    public IWriter getWriter() {
        return writer;
    }

    public void addFriends(UserDisplayInfo u) {
        friends.add(u);
    }

    public void removeFriends(UserDisplayInfo toRemove) {
        for (UserDisplayInfo uInfo : friends) {
            if (toRemove.getUsername() == uInfo.getUsername()) {
                friends.remove(uInfo);
                return;
            }
        }
    }
    public List<UserDisplayInfo> getFriends(){
        return friends;
    }
}
