package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.IController;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.*;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the IService interface.
 * It communicates via Sockets to the server.
 */
public class Service implements IService{
    private Socket socket;
    private ObjectOutputStream messageOutStream;
    private ObjectInputStream messageInStream;
    private ClientModel model;
    private Message messageIn;
    private Message lastMsg;
    private List<UserDisplayInfo> comingFriendsList = new ArrayList();
    private IController controller;
    private boolean running = true;
    private final String serverIP;

    /**
     * Constructor
     * @param model the client model
     * @param controller the controller
     */
    public Service(ClientModel model, IController controller)
    {
        this.model = model;
        this.controller = controller;
        this.serverIP = model.getServerIP();
    }
    /**
     * Connect client to the server
     */
    @Override
    public void connectToS(){
        try {
            socket = new Socket(serverIP, 9000);
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
                    if(!socket.isConnected()){
                        System.out.println("DISCONNECTED FROM SERVER");
                    }
                    messageIn = (Message) messageInStream.readObject();
                    if (messageIn != null) {
                        MessageType msgType = MessageType.valueOf(messageIn.getType().getSimpleName().toUpperCase());
                        switch (msgType) {
                            case ARRAYLIST: {   // A message with an ARRAYLIST contains information about currently active users
                                if (!messageIn.equals(lastMsg) && messageIn != null) {    // The Thread updates the models state
                                    receiveArrayList();
                                }
                                break;
                            }
                            case STRING: {  // A message with a STRING is a text message to be shown in the GUI
                                if (!messageIn.equals(lastMsg) && messageIn != null) {
                                    receiveString();
                                }
                                break;
                            }
                            case FILE: {    // A message with a FILE is intended to be saved to the users local device
                                    if(!messageIn.equals(lastMsg) && messageIn != null){  // The FILE within the message is corrupt so the Thread saves the FILE using a seperate stream of bytes
                                        receiveFile();
                                    }
                                break;
                                }
                             }
                         }
                    }
                } catch(SocketException e){
                    if(e.toString().contains("Connection reset")){
                        System.out.println("Connection reset!");
                        controller.connectionEnded();

                    }
                } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     *
     * handling the recieved array list
     */
    public void receiveArrayList(){
        comingFriendsList = (ArrayList) messageIn.getData();
        model.setUserList(comingFriendsList);
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

    /**
     * handling the recieved STRING
     */
    public void receiveString() throws IOException {
        model.addReceivedMessage(messageIn);    // The Thread updates the models state
        lastMsg = messageIn;
        displayNewMessage(messageIn);
    }

    /**
     * handling the recieved file
     * @throws IOException
     */
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

    /**
     * Setting the message in the outputstream of the socket
     * @param messageOut the message
     * @throws IOException
     */
    public void setMessageOut(Message messageOut) throws IOException {   // Writes a message to the outStream so the Server or whatever else it is connected to can read from it
        messageOutStream.writeObject(messageOut);
        System.out.println("Message sent: " + messageOut.getData());

    }

    /**
     * Safely stops all connections and stops the Thread
     */
    public void stop(){
        running = false;
        try {
            messageOutStream.close();
            messageInStream.close();
            socket.shutdownOutput();
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decides how messages are sent to the Server
     * @param message the sent message
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {
        MessageType msgType = MessageType.valueOf(message.getType().getSimpleName().toUpperCase());
        switch(msgType){
            case STRING: setMessageOut(message);  // Messages containing Strings are sent to the outputStream
                model.addSentMessage(message);    // Changes the models state so that the message can be saved to the device later
                break;
            case COMMAND: setMessageOut(message); // Commands are simply sent to the Server to let the Server perform said command
                break;
            case ARRAYLIST: {
                setMessageOut(message);           // Sends an updated list of friends to the Server so that the Servers state can be updated
                break;
            }
            case FILE: setMessageOut(message);    // Messages containing a FILE will be sent via the message object but arrives corrupt at the Server
                        byte[] sentFile = new byte[(int)model.getSelectedFile().length()];  // Sending a FILE is instead done by sending an array of bytes
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
                model.setSelectedFile(null);            // Removes the currently chosen FILE in order to prevent duplicate Files to be sent
                break;
        }
    }

    /**
     * Informs the controller that it should display a new message in the GUI
     * @param message the message to display
     * @throws IOException
     */
    public void displayNewMessage(Message message) throws IOException {
        controller.displayNewMessage(message);
    }

    /**
     * Informs the controller that it should update
     * @throws IOException
     */
    public void displayFriendList() throws IOException {
        controller.showOnlineFriends(model.getUserList());  // the friendsList so that the Client correctly shows active users
    }

    /**
     * Informs the Server that a user has connected
     * so that the Server can identify the user
     * @param sCommand the sent command
     * @param userName  the username of the user who sends the command
     */
    public void login(CommandName sCommand, String userName){
        try {
            sendMessage(MessageFactory.createCommandMessage(new Command(sCommand,userName),userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
