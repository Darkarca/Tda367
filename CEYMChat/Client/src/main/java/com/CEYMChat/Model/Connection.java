package com.CEYMChat.Model;

import com.CEYMChat.Message;
import com.CEYMChat.MessageFactory;
import sun.rmi.transport.ObjectTable;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *  This class should uphold the connection. This might be implemented as part of the "Session" instead.
 */

public class Connection extends Thread {
    Socket socket;

    ObjectOutputStream messageOutStream;

    ObjectInputStream messageInStream;
    ClientModel model;
    Message messageIn;
    Message messageOut;
    public Connection(ClientModel model){
        this.model = model;



    }
    @Override
    public void start() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8989);
                System.out.println("Thread started");
                this.messageOutStream = new ObjectOutputStream(socket.getOutputStream());
                this.messageInStream = new ObjectInputStream(socket.getInputStream());
                    while(true){
                           messageIn = getMessageIn();
                           /*TODO
                           * Fixa så meddelandet inte printas om och om igen
                           * */
                           if(messageIn != null) {
                               System.out.println("Message received from " + messageIn.getSender() +": "+messageIn.getData());
                              // model.displayNewMessage(messageIn);
                           }

                    }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Message getMessageIn() {
        try{
            return (Message)messageInStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m);
    }

    public Socket getSocket() {
        return socket;
    }




}
