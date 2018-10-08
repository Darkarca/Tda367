package com.CEYMChat;

import java.net.Socket;

public class User {
    private String username;
    private Reader reader;
    private Writer writer;


    public User(Socket socket, ServerModel model) {
        this.writer = new Writer(model, socket);
        this.reader = new Reader(model, socket);
        reader.setUsername(username);


        Thread rThread = new Thread(this.reader);
        Thread wThread = new Thread(this.writer);
        rThread.start();
        wThread.start();

    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public Writer getWriter() {
        return writer;
    }

}
