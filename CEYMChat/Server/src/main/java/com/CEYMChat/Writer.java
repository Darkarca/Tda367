package com.CEYMChat;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Writer implements Runnable {
    ServerModel model;
    Socket socket;
    ObjectOutputStream outputStream;
    Message outMessage;




    public Writer(ServerModel model, Socket socket) {
        this.model = model;
        this.socket = socket;
        {
            try {
                outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }

    }


    @Override
    public void run() {

        while(true){
            try {
                outputStream.writeObject(outMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOutMessage(Message m){
        outMessage = m;
    }
}