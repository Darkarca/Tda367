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
    DataOutputStream messageOutStream;
    DataInputStream messageInStream;
    Message messageIn;
    Message messageOut;
    public Connection(){



    }


    @Override
    public void start() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8989);
                this.messageInStream = new DataInputStream(socket.getInputStream());
                this.messageOutStream = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        ).start();
    }




}
