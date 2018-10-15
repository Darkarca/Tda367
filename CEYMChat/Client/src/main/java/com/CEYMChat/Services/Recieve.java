package com.CEYMChat.Services;

import com.CEYMChat.ClientController;
import com.CEYMChat.Message;
import com.CEYMChat.Model.ClientModel;
import com.CEYMChat.UserDisplayInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Recieve extends Thread {
    private Socket socket;
    IService connection;
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private ClientModel model;
    private Message messageIn;
    private Message lastMsg;
    private ArrayList<UserDisplayInfo> comingFriendsList = new ArrayList();
    private ClientController controller;

    enum MessageType {
        Command,
        String,
        ArrayList
    }
    public Recieve(Socket socket, ObjectInputStream messageInStream, ObjectOutputStream messageOutStream  ){
        this.socket = socket;
        this.messageInStream = messageInStream;
        this.messageOutStream = messageOutStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                messageIn = (Message) messageInStream.readObject();
                if (messageIn != null) {
                    Recieve.MessageType msgType = Recieve.MessageType.valueOf(messageIn.getType().getSimpleName());

                    if (msgType.equals(Recieve.MessageType.String)) {
                        if (messageIn != lastMsg && messageIn != null) {
                            System.out.println("Message received from " + messageIn.getSender() + ": " + messageIn.getData());
                            lastMsg = messageIn;
                            //model.displayNewMessage(getMessageIn());
                            displayNewMessage(messageIn);
                        }

                    } else if (msgType.equals(Recieve.MessageType.ArrayList)) {
                        comingFriendsList = (ArrayList) messageIn.getData();
                        model.createFriendList(comingFriendsList);
                        System.out.println("new friend list has come");
                        //model.logout();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
}


