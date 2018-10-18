package com.CEYMChat;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class  ServerModel {
    private ServerSocket serverSocket;
    private List<User> userList = new ArrayList<>();
    private int port = 9000;
    {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void performCommand(Command c, String sender) {
        switch(c.getCommandName()){
            case SET_USER: {
                userList.get(userList.size()-1).setUsername(c.getCommandData());
                System.out.println("Command performed: 'setUser'" + c.getCommandData());
                break;
            }
            case REFRESH_FRIENDLIST:
                User u = getUserByUsername(sender);
                u.sendInfo(sendUserInfo());
                System.out.println("Command performed: 'refreshFriendList '" + c.getCommandData());
                break;
            case DISCONNECT: userList.remove(getUserByUsername(sender));
                break;
            case REGISTER:
                break;
            case ADD_FRIEND:
                break;
            case REQUEST_CHAT:
        }
    }

    public Message sendUserInfo(){
        List<UserDisplayInfo> list = new ArrayList<UserDisplayInfo>();
        for (User u:userList) {
            UserDisplayInfo u1 = new UserDisplayInfo();
            u1.setUsername(u.getUsername());
            list.add(u1);
        }
        return MessageFactory.createFriendInfoList(list);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void addUser(User u) {
        userList.add(u);
    }
    public void displayMessage(Message m) throws IOException, ClassNotFoundException {
        System.out.println(m.getSender() + ": " + m.getData());
    }
    public void sendMessage(Message m, String receiver){
        User u = getUserByUsername(receiver);
        u.sendMessage(m);
    }
    public User getUserByUsername(String username){
        for (User u : userList){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }
}
