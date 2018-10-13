package com.CEYMChat.Services;

import com.CEYMChat.*;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class should uphold the connection. This might be implemented as part of the "Session" instead.
 */

public class Connection implements IService{
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private Message messageIn;
    private Message lastMsg;
    private ArrayList<UserDisplayInfo> comingFriendsList = new ArrayList();
    private ClientController controller;

    public Connection(ClientController controller){
        this.controller = controller;
    }

    enum MessageType {
        Command,
        String,
        ArrayList
    }

    @Override
    public void run() {
            try {
                System.out.println("Thread started");

                //this.comingData = this.messageInStream = new ObjectInputStream(socket.getInputStream());

                this.messageOutStream = new ObjectOutputStream(controller.getModel().getSocket().getOutputStream());
                this.messageInStream = new ObjectInputStream(controller.getModel().getSocket().getInputStream());

                while (true) {

                    messageIn = (Message) messageInStream.readObject();
                    if (messageIn != null) {
                        MessageType msgType = MessageType.valueOf(messageIn.getType().getSimpleName());

                        if (msgType.equals(MessageType.String)) {
                            if (messageIn != lastMsg && messageIn != null) {
                                System.out.println("Message received from " + messageIn.getSender() + ": " + messageIn.getData());
                                lastMsg = messageIn;
                                //getModel().displayNewMessage(getMessageIn());
                                displayNewMessage(messageIn);
                            }

                        } else if (msgType.equals(MessageType.ArrayList)) {
                            comingFriendsList = (ArrayList) messageIn.getData();
                            controller.getModel().createFriendList(comingFriendsList);
                            System.out.println("new friend list has come");

                            Platform.runLater(
                                    () -> {
                                        // Updating the UI
                                        try {
                                            displayFriendList();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );

                            //getModel().logout();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void sendCommandMessage(String sCommand, String sData) throws IOException {
        Message message = MessageFactory.createCommandMessage(new Command(sCommand, sData), controller.getModel().getUsername());
        System.out.println("Command sent: " + sCommand + " with data: " + sData);
        setMessageOut(message);
    }

    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());
    }

    public void sendStringMessage(String toSend, String receiver) throws IOException {
        Message message = MessageFactory.createStringMessage(toSend, controller.getModel().getUsername(), receiver);
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
        controller.showOnlineFriends(controller.getModel().getfriendList());
        System.out.println("new friend list updating 1");

    }

}
