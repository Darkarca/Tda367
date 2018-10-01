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

    List<Socket> socketList = new ArrayList<Socket>();
    DataInputStream messageInStream;
    DataOutputStream messageOutStream;

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

    public void performCommand(Command c) {
    }

    public void startSession() {

    }

    public boolean checkUser() {
        return false;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void addSocket(Socket s) {
        socketList.add(s);
    }

    public void addUser(User u) {
        userList.add(u);
    }


    public void displayMessage(String s) {
        System.out.println(s);
    }
    public void startReadThreads(){
        for (ReadThread rt : readThreads){
            rt.run();
        }

    }


   /* public synchronized void initiateConnection() throws IOException {


            while(true)
                for (Socket s:socketList) {
                    try {
                    messageInStream = new DataInputStream(s.getInputStream());
                    //messageOutStream = new DataOutputStream(s.getOutputStream());
                  //  displayMessage(getMessage());
                    //System.out.println("Connection initiated");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(socketList.size());
            }
    }*/




    public synchronized Message getMessage(){
        try {
            return new Message(messageInStream.readUTF());
        } catch (Exception e) {
           // e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
