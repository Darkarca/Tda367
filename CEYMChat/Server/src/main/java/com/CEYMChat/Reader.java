package com.CEYMChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Thread that reads user input and send it to the server.
 */

public class Reader implements Runnable {
    ServerModel model;
    Socket socket;
    ObjectInputStream inputStream;
    Message inMessage;
    String username;
    enum MessageType{
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
                System.out.println("I'am here");
                MessageType msgType = MessageType.valueOf(inMessage.getType().getSimpleName());
                switch(msgType) {
                    case Command:{
                        System.out.println("Message type: Command");
                        model.performCommand((Command) inMessage.getData(), this);
                    }
                    case String: {
                        System.out.println("Message type: String");
                        model.displayMessage(inMessage);
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

    public String getUserName() {
        try {
            Message usernameMSG = (Message)inputStream.readObject();
            Command c = (Command)usernameMSG.getData();
            username = c.getCommandData();
            return username;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




}
