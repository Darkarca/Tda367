package com.CEYMChat;

import java.net.Socket;

public class User {

    Socket socket;
    String username;
    ReadThread readThread;
    ServerModel model;


    public User(Socket s, ServerModel model) {
        this.model = model;
        this.socket = s;
        this.readThread = new ReadThread(model, this.socket);
        readThread.username = username;


        Thread thread = new Thread(this.readThread);
        thread.start();
    }

}
