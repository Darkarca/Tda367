package com.CEYMChat;

import java.net.Socket;

public class User {

    Socket socket;


     String username;



    private Reader reader;
    private Writer writer;
    private ServerModel model;


    public User(Socket s, ServerModel model) {
        this.model = model;
        this.socket = s;
        this.writer = new Writer(model, this.socket);
        this.reader = new Reader(model, this.socket);
        reader.setUsername(username);


        Thread rThread = new Thread(this.reader);
        Thread wThread = new Thread(this.writer);
        rThread.start();
        wThread.start();

    }

    public String getUsername() {
        return username;
    }
    public Reader getReader() {
        return reader;
    }

    public Writer getWriter() {
        return writer;
    }

}
