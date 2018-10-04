package com.CEYMChat;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class  ServerModel {

    ServerSocket serverSocket;
    List<User> userList = new ArrayList<>();

    {
        try {
            serverSocket = new ServerSocket(8989);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ObjectInputStream messageInStream;
    ObjectOutputStream messageOutStream;

   /* public void logInUser(Command c) {
        if (checkUser(c.getCommandData())) {
            try {
                int newUser = clientList.size();
                clientList.add(new Socket());
                Socket thisLogIn;
                thisLogIn = clientList.get(newUser);
                thisLogIn = ServerMain.serverSocket.accept();
                messageInStream = new ObjectInputStream(thisLogIn.getInputStream());
                messageOutStream = new ObjectOutputStream(thisLogIn.getOutputStream());

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }*/

    public void registerUser() {

    }

    public void performCommand(Command c, String sender) {
        switch(c.getCommandName()){

            case("setUser"): userList.get(userList.size()-1).setUsername(c.getCommandData());
            System.out.println("Command performed: 'setUser'");
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


    public synchronized Message getMessage(){
        try {

            return (Message) messageInStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
