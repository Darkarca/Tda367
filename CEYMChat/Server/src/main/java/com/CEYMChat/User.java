package com.CEYMChat;

import java.net.Socket;

public class User {

    Socket socket;
    String username;
    Reader reader;
    Writer writer;
    ServerModel model;


    public User(Socket s, ServerModel model) {
        this.model = model;
        this.socket = s;
        this.writer = new Writer(model, this.socket);
        this.reader = new Reader(model, this.socket);
        reader.username = username;


        Thread rThread = new Thread(this.reader);
        Thread wThread = new Thread(this.writer);
        rThread.start();
        wThread.start();

    }

}
