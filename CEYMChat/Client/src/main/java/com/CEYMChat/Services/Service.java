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

            /** Starts a new thread constantly reading a inputstream from the Server.
             * While it is running it continuously checks the stream,
             * checks what type of message it has received and processes it appropriately
             **/
    @Override
    public void read() {
        new Thread(() -> {
            try {
                while (running) {
                    messageIn = (Message) messageInStream.readObject();
                    if (messageIn != null) {
                        MessageType msgType = MessageType.valueOf(messageIn.getType().getSimpleName());
                        switch (msgType) {
                            case ArrayList: {   // A message with an ArrayList contains information about currently active users
                                if (messageIn != lastMsg && messageIn != null) {    // The Thread updates the models state
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
                            case String: {  // A message with a String is a text message to be shown in the GUI
                                if (messageIn != lastMsg && messageIn != null) {
                                    model.addReceivedMessage(messageIn);    // The Thread updates the models state
                                    System.out.println("Message received from " + messageIn.getSender() + ": " + messageIn.getData());
                                    lastMsg = messageIn;
                                    displayNewMessage(messageIn);
                                }
                                break;
                            }
                            case File: {    // A message with a File is intended to be saved to the users local device
                                    if(messageIn != lastMsg && messageIn != null){  // The File within the message is corrupt so the Thread saves the File using a seperate stream of bytes
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

    public void setMessageOut(Message m) throws IOException {   // Writes a message to the outStream so the Server or whatever else it is connected to can read from it
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());

    }
    public void stop(){ // Safely stops all connections and stops the Thread
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

    public void sendMessage(Message m) throws IOException { // Decides how messages are sent to the Server
        MessageType msgType = MessageType.valueOf(m.getType().getSimpleName());
        switch(msgType){
            case String: setMessageOut(m);
                model.addSentMessage(m);
                break;
            case Command: setMessageOut(m);
                break;
            case ArrayList: {
                setMessageOut(m);
                break;
            }
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
        //controller.
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
