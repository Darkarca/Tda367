package com.CEYMChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Thread that reads user input and send it to the server.
 */

public class Reader implements Runnable {
    private ServerModel model;   //TODO Must not be in user reader/writer therefor (screenshot)


    private Socket socket;
    private ObjectInputStream inputStream;
    private Message inMessage;
    private String username;

    enum MessageType {
        Command,
        String;
    }


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

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            //case(File):
            //  break;
            //case(Image):
            //   break;
            // model.displayMessage((Message)inputStream.readObject());
            // model.sendMessage((Message)inputStream.readObject());


        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
