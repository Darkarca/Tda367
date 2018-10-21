package com.CEYMChatClient.Services;

import com.CEYMChatClient.IController;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.*;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/** This class implements the IService interface. It communicates via Sockets to the server. */

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
    String serverIP;

    /** Constructor */
    public Service(ClientModel model, IController c)
    {
        this.model = model;
        this.controller = c;
        this.serverIP = model.getServerIP();
    }

    /** Getters and setters */
    public ObjectInputStream getMessageInStream() {
        return messageInStream;
    }


    /** Connect client to the server */
    @Override
    public void connectToS(){
        try {
            socket = new Socket(serverIP, 9000);
            System.out.println("Thread started");
            messageOutStream = new ObjectOutputStream(socket.getOutputStream());
            messageInStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connection started");
            read();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Starts a new thread constantly reading a inputstream from the Server.
     * While it is running it continuously checks the stream,
     * checks what type of message it has received and processes it appropriately
     */
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
                                    receiveArrayList();
                                }
                                break;
                            }
                            case String: {  // A message with a String is a text message to be shown in the GUI
                                if (messageIn != lastMsg && messageIn != null) {
                                    receiveString();
                                }
                                break;
                            }
                            case File: {    // A message with a File is intended to be saved to the users local device
                                    if(messageIn != lastMsg && messageIn != null){  // The File within the message is corrupt so the Thread saves the File using a seperate stream of bytes
                                        receiveFile();
                                    }
                                break;
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

    /** handling the received array list */
    public void receiveArrayList(){
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

    /** handling the receive String */
    public void receiveString(){
        model.addReceivedMessage(messageIn);    // The Thread updates the models state
        System.out.println("Message received from " + messageIn.getSender() + ": " + messageIn.getData());
        lastMsg = messageIn;
        displayNewMessage(messageIn);
    }

    /** handling the receive file */
    public void receiveFile() throws IOException {

        byte [] receivedFile  = new byte [1073741824];
        InputStream inputStream = socket.getInputStream();
        FileOutputStream fileOut = new FileOutputStream("Client/messages/" + ((File)messageIn.getData()).getName());
        BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
        int bytesRead = inputStream.read(receivedFile,0,receivedFile.length);
        int current = bytesRead;
        bufferedOut.write(receivedFile, 0 , current);
        bufferedOut.flush();

    }

    /** Setting the message in the outputstream of the socket*/
    public void setMessageOut(Message m) throws IOException {   // Writes a message to the outStream so the Server or whatever else it is connected to can read from it
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m.getData());

    }

    /** Decides how messages are sent to the Server */
    public void sendMessage(Message m) throws IOException {
        MessageType msgType = MessageType.valueOf(m.getType().getSimpleName());
        switch(msgType){
            case String: setMessageOut(m);  // Messages containing Strings are sent to the outputStream
                model.addSentMessage(m);    // Changes the models state so that the message can be saved to the device later
                break;
            case Command: setMessageOut(m); // Commands are simply sent to the Server to let the Server perform said command
                break;
            case ArrayList: {
                setMessageOut(m);           // Sends an updated list of friends to the Server so that the Servers state can be updated
                break;
            }
            case File: setMessageOut(m);    // Messages containing a File will be sent via the message object but arrives corrupt at the Server
                        byte[] sentFile = new byte[(int)model.getSelectedFile().length()];  // Sending a File is instead done by sending an array of bytes
                        FileInputStream fileInput = new FileInputStream(model.getSelectedFile());   // To a separate inputStream
                        BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
                        bufferedInput.read(sentFile,0,sentFile.length);
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(sentFile,0,sentFile.length);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream.flush();
                model.setSelectedFile(null);            // Removes the currently chosen File in order to prevent duplicate Files to be sent
                break;
        }
    }

    /**
     * Stops the clientside connection
     */
    @Override
    public void stop() {
        running = false;
    }

    /* Informs the controller that it should display a new message in the GUI */
    public void displayNewMessage(Message m){
        controller.displayNewMessage(m);
    }


    /** Informs the controller that it should update */
    public void displayFriendList() throws IOException {
        controller.showOnlineFriends(model.getUserList());  // the friendsList so that the Client correctly shows active users
        System.out.println("New list of friends displayed");
    }

    /** Informs the Server that a user has connected so that the Server can identify the user */
    public void login(CommandName sCommand, String userName){
        try {
            sendMessage(MessageFactory.createCommandMessage(new Command(sCommand,userName),userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
