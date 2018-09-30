package com.CEYMChat;

import com.CEYMChat.Message;
import sun.rmi.transport.ObjectTable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *  This class should uphold the connection. This might be implemented as part of the "Session" instead.
 */

public class Connection extends Thread {
    Socket socket;
    ObjectOutputStream messageOutStream;
    ObjectInputStream messageInStream;
    Message messageIn;
    Message messageOut;

    public Connection(){
    this.start();
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 6969);
                this.messageInStream = new ObjectInputStream(socket.getInputStream());
                this.messageOutStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
            try {
                messageOutStream.writeObject(messageOut);
                System.out.print("Message to be sent");
            } catch (IOException e) {
                e.printStackTrace();
                }
            }


        });
    }

    public ObjectOutputStream getMessageOutStream() {
        return messageOutStream;
    }

    public ObjectInputStream getMessageInStream() {
        return messageInStream;
    }

    public void setMessageOut(Message messageOut) {
        this.messageOut = messageOut;
    }
    public Message getInMessage(){
        return null;
    }




}
