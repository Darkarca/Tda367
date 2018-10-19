package com.CEYMChat.Services;

import com.CEYMChat.Command;
import com.CEYMChat.CommandName;
import com.CEYMChat.Message;
import com.CEYMChat.MessageType;
import com.CEYMChat.Model.ServerModel;

import java.io.*;
import java.net.Socket;
/**
 * Thread that reads user input and send it to the server.
 */
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

    public void stop(){
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
                inMessage = (Message) inputStream.readObject();
                MessageType msgType = MessageType.valueOf(inMessage.getType().getSimpleName());
                switch (msgType) {
                    case Command: {
                        System.out.println("Message type: Command");
                        model.performCommand((Command) inMessage.getData(), inMessage.getSender());
                        if(((Command)inMessage.getData()).getCommandName() == CommandName.DISCONNECT){
                            this.stop();
                        }
                        break;
                    }
                    case String: {
                        System.out.println("Message type: String");
                        model.displayMessage(inMessage);
                        model.sendMessage(inMessage, inMessage.getReceiver());
                        break;
                    }
                    case ArrayList: {
                        System.out.println("Updating friendslist for: " + inMessage.getSender());
                        model.getUserByUsername(inMessage.getSender()).syncFriends(inMessage);
                        model.performCommand(new Command(CommandName.REFRESH_FRIENDLIST,inMessage.getSender()),inMessage.getSender());
                        break;
                    }
                    case File: {
                        byte [] receivedFile  = new byte [1073741824];
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

