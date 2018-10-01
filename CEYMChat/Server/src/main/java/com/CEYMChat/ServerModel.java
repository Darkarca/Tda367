package com.CEYMChat;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerModel {

    ServerSocket serverSocket;

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

    public void displayMessage(Message m) {
        System.out.println(m.getData().toString());
    }


    public void initiateConnection() throws IOException {
        new Thread(() -> {
            int i = 0;
            while(true){

            if(socketList.size() > i) {

                try {
                    messageInStream = new DataInputStream(socketList.get(i).getInputStream());
                    messageOutStream = new DataOutputStream(socketList.get(i).getOutputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Connection initiated");
            i++;
            }
            }
        }
        ).start();
    }

    public Message getMessage(){
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
