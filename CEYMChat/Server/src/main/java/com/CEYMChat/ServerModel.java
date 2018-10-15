package com.CEYMChat;


import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class  ServerModel {

    private ServerSocket serverSocket;
    private List<User> userList = new ArrayList<>();
    private boolean inlogged = false;

    {
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void performCommand(Command c, String sender) {
        switch(c.getCommandName()){

            case("setUser"): {
                userList.get(userList.size()-1).setUsername(c.getCommandData());
                System.out.println("Command performed: 'setUser'" + c.getCommandData());
                inlogged = true;
                break;
            }
            case("refreshFriendList"):
                User u = getUserByUsername(sender);
                Writer w = u.getWriter();
                w.setOutMessage(w.createUserInfoList(userList));
                break;
            case("disconnect"): userList.remove(getUserByUsername(sender));
                break;
            case("register"):
                break;
            case("addFriend"):
                break;
            case("requestChat"):
                // createSession(getUserByUsername(c.getCommandData()),getUserByUsername(c.getSender()));
        }

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
        u.getWriter().setOutMessage(m);
    }

    public User getUserByUsername(String username){
        for (User u : userList){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public boolean isLoggedIn() {
        return inlogged;
    }

    public void setLoggedIn(boolean value) {
        inlogged = value;
    }
}
