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

public class Service implements IService{
    private Socket socket;
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private ClientModel model;
    private Message messageIn;
    private Message lastMsg;
    private ArrayList<UserDisplayInfo> comingFriendsList = new ArrayList();
    private IController controller;
    DataOutputStream dataOut;
    DataInputStream dataIn;
    private boolean running = true;

    public Service(ClientModel model, IController c)
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
                while (running) {
                    messageInStream = new ObjectInputStream(socket.getInputStream());
                    messageIn = (Message) messageInStream.readObject();
                    if (messageIn != null) {
                        MessageType msgType = MessageType.valueOf(messageIn.getType().getSimpleName());
                        switch (msgType) {
                            case ArrayList: {
                                if (messageIn != lastMsg && messageIn != null) {
                                    comingFriendsList = (ArrayList) messageIn.getData();
                                    model.setUserList(comingFriendsList);
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
                                        byte [] receivedFile  = new byte [1073741824];
                                        InputStream inputStream = socket.getInputStream();
                                        FileOutputStream fileOut = new FileOutputStream("Client/messages/" + ((File)messageIn.getData()).getName());
                                        BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
                                        int bytesRead = inputStream.read(receivedFile,0,receivedFile.length);
                                        int current = bytesRead;
                                        bufferedOut.write(receivedFile, 0 , current);
                                        bufferedOut.flush();
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

    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());

    }
    public void stop(){
        running = false;
        try {
            messageOutStream = null;
            messageInStream = null;
            socket.shutdownOutput();
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        byte[] sentFile = new byte[(int)model.getSelectedFile().length()];
                        FileInputStream fileInput = new FileInputStream(model.getSelectedFile());
                        BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
                        bufferedInput.read(sentFile,0,sentFile.length);
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(sentFile,0,sentFile.length);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream.flush();
                model.setSelectedFile(null);
                break;
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
        controller.showOnlineFriends(model.getUserList());
        System.out.println("New list of friends displayed");
    }

    public void login(CommandName sCommand, String userName){
        try {
            sendMessage(MessageFactory.createCommandMessage(new Command(sCommand,userName),userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
