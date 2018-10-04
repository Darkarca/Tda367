package com.CEYMChat;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Writer implements Runnable {
    ServerModel model;
    Socket socket;
    ObjectOutputStream outputStream;
    Message outMessage;
    Message lastMsg;




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
                if(outMessage != lastMsg) {
                    lastMsg = outMessage;
                    System.out.println( outMessage.getSender() + ": " + outMessage.getData());

                    // System.out.println("Object written to stream: " + outMessage.toString());
                   // System.out.println("the received message is " + outMessage.toString());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOutMessage(Message m){
        System.out.println( m.getSender() + ": " + m.getData());
        this.outMessage = m;

    }
}