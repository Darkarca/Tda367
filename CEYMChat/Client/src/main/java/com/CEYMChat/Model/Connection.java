package com.CEYMChat.Model;

import com.CEYMChat.Message;
import sun.rmi.transport.ObjectTable;

import java.io.*;
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



    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8989);
                System.out.println("Thread strated");
                this.messageOutStream = new ObjectOutputStream(socket.getOutputStream());
                //this.messageInStream = new ObjectInputStream(socket.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        ).start();
    }

    public void setMessageOut(Message m) throws IOException {
        System.out.println("MessageOutputStream: " + messageOutStream);
        messageOutStream.writeObject(m);
        System.out.println("Message sent: " + m);
    }




}
