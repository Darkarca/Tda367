package com.CEYMChat.Model;

import com.CEYMChat.Message;
import com.CEYMChat.MessageFactory;
import com.CEYMChat.UserDisplayInfo;
import javafx.application.Platform;
import sun.rmi.transport.ObjectTable;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class should uphold the connection. This might be implemented as part of the "Session" instead.
 */

public class Connection extends Thread {
    private Socket socket;
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private ClientModel model;
    private Message messageIn;
    private Message lastMsg;
    private ArrayList<UserDisplayInfo> comingFriendsList = new ArrayList();

    public Connection(ClientModel model) {
        this.model = model;
    }

    enum MessageType {
        Command,
        String,
        ArrayList
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 9000);
                System.out.println("Thread started");

                //this.comingData = this.messageInStream = new ObjectInputStream(socket.getInputStream());

                this.messageOutStream = new ObjectOutputStream(socket.getOutputStream());
                this.messageInStream = new ObjectInputStream(socket.getInputStream());

                while (true) {

                    messageIn = (Message) messageInStream.readObject();
                    if (messageIn != null) {
                        MessageType msgType = MessageType.valueOf(messageIn.getType().getSimpleName());

                        if (msgType.equals(MessageType.String)) {
                            if (messageIn != lastMsg && messageIn != null) {
                                System.out.println("Message received from " + messageIn.getSender() + ": " + messageIn.getData());
                                lastMsg = messageIn;
                                //model.displayNewMessage(getMessageIn());
                                model.displayNewMessage(messageIn);
                            }

                        } else if (msgType.equals(MessageType.ArrayList)&& model.getLoginStatus() == true) {
                            comingFriendsList = (ArrayList) messageIn.getData();
                            model.setFriendList(comingFriendsList);
                            Platform.runLater(
                                    () -> {
                                        // Update UI here.
                                        try {
                                            model.displayFriendList();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );
                            model.logout();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
/*
    private Object getMessageIn() {
        try{
            if (messageInStream.readObject() instanceof Message)
                return (Message)messageInStream.readObject();
            else
                return (List)messageInStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }
    */


    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());
    }

    public Socket getSocket() {
        return socket;
    }

}
