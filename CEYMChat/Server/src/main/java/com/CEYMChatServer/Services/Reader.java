package com.CEYMChatServer.Services;

import com.CEYMChatLib.Command;
import com.CEYMChatLib.CommandName;
import com.CEYMChatLib.Message;
import com.CEYMChatLib.MessageType;
import com.CEYMChatServer.Model.ServerModel;
import java.io.*;
import java.net.Socket;
/** Thread that reads user input and send it to the server. */
public class Reader implements Runnable, IReader {
    private ServerModel model;
    private Socket socket;
    private ObjectInputStream inputStream;
    private Message inMessage;

    private File receivedFile;

    private boolean running = true;


    public Reader(ServerModel model, Socket socket) {
        this.model = model;
        this.socket = socket;
        {
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }
        Thread rThread = new Thread((Runnable) this);
        rThread.start();
    }

    public void stop(){     // Safely disconnects the socket and stops the Thread
        try {
            running = false;
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                inMessage = (Message) inputStream.readObject();         // Constantly check the inputStream and casts its object to a message
                MessageType msgType = MessageType.valueOf(inMessage.getType().getSimpleName());
                switch (msgType) {
                    case Command: {                                     // A message containing a command is sent to the model so that is can be performed, we stop the thread if the command tells us to disconnect
                        System.out.println("Message type: Command");
                        model.performCommand((Command) inMessage.getData(), inMessage.getSender());
                        if(((Command)inMessage.getData()).getCommandName() == CommandName.DISCONNECT){
                            this.stop();
                        }
                        break;
                    }
                    case String: {                                      // A string message is simply sent to the model and redistributed to the correct client
                        System.out.println("Message type: String");
                        model.displayMessage(inMessage);
                        model.sendMessage(inMessage, inMessage.getReceiver());
                        break;
                    }
                    case ArrayList: {                                   // An arrayList is sent to the model so that the friendlist of a specific user can be updated
                        System.out.println("Updating friendslist for: " + inMessage.getSender());
                        model.getUserByUsername(inMessage.getSender()).syncFriends(inMessage);
                        model.performCommand(new Command(CommandName.REFRESH_FRIENDLIST,inMessage.getSender()),inMessage.getSender());
                        break;
                    }
                    case File: {                                        // A a message containing a File is received and redistributed, the File will be corrupt so it needs to be received via a separate inputStream.
                        byte [] receivedFile  = new byte [1073741824];  // The message with the original File still contains information such as filesize and filename
                        InputStream inputStream = socket.getInputStream();
                        FileOutputStream fileOut = new FileOutputStream("Server/messages/" + ((File)inMessage.getData()).getName());
                        BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
                        int bytesRead = inputStream.read(receivedFile,0,receivedFile.length);
                        int current = bytesRead;

                            bufferedOut.write(receivedFile, 0 , current);
                            bufferedOut.flush();

                        System.out.println("Message type: File");
                        System.out.println("Filename: " + ((File)inMessage.getData()).getName());
                        model.sendFile("Server/messages/" + ((File)inMessage.getData()).getName(), inMessage);
                        //model.sendMessage(inMessage, inMessage.getReceiver());
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

