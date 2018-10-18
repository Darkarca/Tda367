package com.CEYMChat.Services;

import com.CEYMChat.Command;
import com.CEYMChat.Message;
import com.CEYMChat.MessageType;
import com.CEYMChat.ServerModel;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
/**
 * Thread that reads user input and send it to the server.
 */
public class Reader implements Runnable, IReader {
    private ServerModel model;
    private Socket socket;
    private ObjectInputStream inputStream;
    private Message inMessage;

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
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                inMessage = (Message) inputStream.readObject();
                MessageType msgType = MessageType.valueOf(inMessage.getType().getSimpleName());
                switch (msgType) {
                    case Command: {
                        System.out.println("Message type: Command");
                        model.performCommand((Command) inMessage.getData(), inMessage.getSender());
                        break;
                    }
                    case String: {
                        System.out.println("Message type: String");
                        model.displayMessage(inMessage);
                        model.sendMessage(inMessage, inMessage.getReceiver());
                        break;
                    }
                    case File: {
                        System.out.println("Message type: File");
                        System.out.println("Filename: " + ((File)inMessage.getData()).getName());
                        model.sendMessage(inMessage, inMessage.getReceiver());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

