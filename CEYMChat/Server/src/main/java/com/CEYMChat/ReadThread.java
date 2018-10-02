package com.CEYMChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Thread that reads user input and send it to the server.
 */

public class ReadThread implements Runnable {
    ServerModel model;
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    Message inMessage;
    String username;


    public ReadThread(ServerModel model, Socket socket) {
        this.model = model;
        this.socket = socket;
        {
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
                outputStream = new ObjectOutputStream(this.socket.getOutputStream());
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
                System.out.println(inMessage.getData().getClass());
                if (inMessage.getData().getClass().equals(new Command("s", "s").getClass())) {
                    System.out.println("Message type: Command");
                    model.performCommand((Command)inMessage.getData(), this);
                } else if (inMessage.getData().getClass().equals("s".getClass())) {
                    System.out.println("Message type: String");
                    model.displayMessage(inMessage);
                    break;
                }
                //case(File):
                //  break;
                //case(Image):
                //   break;
               // model.displayMessage((Message)inputStream.readObject());
               // model.sendMessage((Message)inputStream.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


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
