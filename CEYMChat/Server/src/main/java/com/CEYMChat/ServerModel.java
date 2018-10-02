package com.CEYMChat;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class    ServerModel {

    ServerSocket serverSocket;
    List<ReadThread> readThreads = new ArrayList<>();
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

    public void performCommand(Command c, ReadThread t) {
        switch(c.getCommandName()){
            case("setUser"): userList.get(userList.indexOf(t)+1).username = c.getCommandData();
            System.out.println("Command performed: 'setUser'");
                break;
            case("disconnect"): userList.remove(t);
                break;
            case("register"):
                break;
            case("addFriend"):
                break;

        }

    }


    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void addUser(User u) {
        userList.add(u);
    }

    public void displayMessage(Message m) {
        System.out.println(m.getSender() + ": " + m.getData());
    }


    public synchronized Message getMessage(){
        try {
            return (Message) messageInStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public synchronized void sendMessage(Message m) throws IOException {
            messageOutStream.writeObject(m);
    }
}
