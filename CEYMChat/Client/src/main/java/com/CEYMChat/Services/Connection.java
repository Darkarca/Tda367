package com.CEYMChat.Services;

import com.CEYMChat.*;
import com.CEYMChat.Model.ClientModel;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class should uphold the connection. This might be implemented as part of the "Session" instead.
 */

public class Connection extends Thread implements IService{
    private Socket socket;
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private ClientModel model;
    private Message messageIn;
    private Message lastMsg;
    private ArrayList<UserDisplayInfo> comingFriendsList = new ArrayList();
    private ClientController controller;

    public Connection(ClientModel model) {
        this.model = model;
    }

    enum MessageType {
        Command,
        String,
        ArrayList
    }

    @Override
    public void start(ClientController c) {
        new Thread(() -> {
            try {
                this.controller = c;
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
                                displayNewMessage(messageIn);
                            }

                        } else if (msgType.equals(MessageType.ArrayList)&& model.getLoginStatus() == true) {
                            comingFriendsList = (ArrayList) messageIn.getData();
                            model.setFriendList(comingFriendsList);
                            Platform.runLater(
                                    () -> {
                                        // Update UI here.
                                        try {
                                            displayFriendList();
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

    public void sendCommandMessage(String sCommand, String sData) throws IOException {
        Message message = MessageFactory.createCommandMessage(new Command(sCommand, sData), model.getUsername());
        System.out.println("Command sent: " + sCommand + " with data: " + sData);
        setMessageOut(message);
    }

    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());
    }

    public void sendStringMessage(String toSend, String receiver) throws IOException {
        Message message = MessageFactory.createStringMessage(toSend, model.getUsername(), receiver);
        System.out.println(message.getSender() + ": " + message.getData().toString());
        setMessageOut(message);
    }

    public void displayNewMessage(Message m){
        String toDisplay;
        toDisplay = processMessage(m);
        controller.displayNewMessage(toDisplay);
    }


    public String processMessage(Message m) {
        String processedMessage;
        processedMessage = m.getSender() + ": " + m.getData().toString();
        return processedMessage;
    }

    public void displayFriendList() throws IOException {
        controller.showOnlineFriends(comingFriendsList);
    }

}
