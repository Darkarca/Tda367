package com.CEYMChat.Services;

import com.CEYMChat.*;
import com.CEYMChat.Model.ClientModel;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/**
 * This class implements the IService interface. It communicates via Sockets to the server.
 */

public class Services implements IService{
    private Socket socket;
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private ClientModel model;
    private Message messageIn;
    private Message lastMsg;
    private ArrayList<UserDisplayInfo> comingFriendsList = new ArrayList();
    private IController controller;
    private File selectedFile;

    public Services(ClientModel model, IController c)
    {
        this.model = model;
        this.controller = c;
    }

    public ObjectInputStream getMessageInStream() {
        return messageInStream;
    }

    /**
     * Enum to decide what type of command is received.
     */
    @Override
    public void connectToS(){
        try {
            socket = new Socket("localhost", 9000);
            System.out.println("Thread started");
            messageOutStream = new ObjectOutputStream(socket.getOutputStream());
            messageInStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection started");
            read();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void read() {
        new Thread(() -> {
            try {
                while (true) {
                    messageIn = (Message) messageInStream.readObject();
                    if (messageIn != null) {
                        MessageType msgType = MessageType.valueOf(messageIn.getType().getSimpleName());
                        switch (msgType) {
                            case ArrayList: {
                                if (messageIn != lastMsg && messageIn != null) {
                                    comingFriendsList = (ArrayList) messageIn.getData();
                                    model.setFriendList(comingFriendsList);
                                    System.out.println("A new list of friends has arrived");
                                    lastMsg = messageIn;
                                    Platform.runLater(
                                            () -> {
                                                try {
                                                    displayFriendList();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                    );
                                }
                                break;
                            }
                            case String: {
                                if (messageIn != lastMsg && messageIn != null) {
                                    model.addReceivedMessage(messageIn);
                                    System.out.println("Message received from " + messageIn.getSender() + ": " + messageIn.getData());
                                    lastMsg = messageIn;
                                    displayNewMessage(messageIn);
                                }
                            }
                                case File: {
                                    if(messageIn != lastMsg && messageIn != null){
                                        System.out.println("File received from " + messageIn.getSender() + ": " + ((File)messageIn.getData()).getName());
                                        File newFile = new File(((File)messageIn.getData()).getName());
                                        FileOutputStream output = new FileOutputStream("Client/messages/" + newFile.getName());
                                        ObjectOutputStream stream = new ObjectOutputStream(output);
                                        stream.writeObject(newFile);
                                        break;
                                    }
                                }
                    }
                }
                    }
                } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendCommandMessage(CommandName sCommand, String sData) throws IOException {
        Message message = MessageFactory.createCommandMessage(new Command(sCommand, sData), model.getUsername());
        System.out.println("Command sent: " + sCommand + " with data: " + sData);
        setMessageOut(message);
    }

    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());

    }

    public void sendMessage(Message m) throws IOException {
        MessageType msgType = MessageType.valueOf(m.getType().getSimpleName());
        switch(msgType){
            case String: setMessageOut(m);
                model.addSentMessage(m);
                break;
            case Command: setMessageOut(m);
                break;
            case File: setMessageOut(m);
                selectedFile = null;
                break;
        }
    }


    public void sendStringMessage(String toSend, String receiver) throws IOException {
        if (selectedFile != null) {
            Message fileMessage = MessageFactory.createFileMessage(selectedFile, model.getUsername(), receiver);
            System.out.println(fileMessage.getSender() + ": sent a file named " + selectedFile.getName());
            setMessageOut(fileMessage);
            selectedFile = null;
        } else {
            Message stringMessage = MessageFactory.createStringMessage(toSend, model.getUsername(), receiver);
            System.out.println(stringMessage.getSender() + ": " + stringMessage.getData().toString());
            setMessageOut(stringMessage);
            model.addSentMessage(stringMessage);

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

    public void displayFriendList() throws IOException {
        controller.showOnlineFriends(model.getFriendList());
        System.out.println("New list of friends displayed");
    }

    public void login(CommandName sCommand, String userName){
        try {
            sendCommandMessage(sCommand,userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }
}
