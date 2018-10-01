package com.CEYMChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Thread that reads user input and send it to the server.
 */

public class ReadThread implements Runnable {
    ServerModel model;
    Socket socket;
    ObjectInputStream inputStream;
    String username;


    public ReadThread(ServerModel model, Socket socket) {
        this.model = model;
        this.socket = socket;
        {
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }


    }


    @Override
    public void run() {
        while (true) {
            try {
                model.displayMessage((Message)inputStream.readObject(), username);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public String getUserName() {
        try {
            Message usernameMSG = (Message)inputStream.readObject();
            username = usernameMSG.getData().toString();
            return usernameMSG.getData().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




}
