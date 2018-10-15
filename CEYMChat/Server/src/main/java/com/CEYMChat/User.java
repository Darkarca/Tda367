package com.CEYMChat;

import com.CEYMChat.Services.IReader;
import com.CEYMChat.Services.IWriter;

import java.net.Socket;

public class User {
    private String username;
    private IReader reader;
    private IWriter writer;


    public User(Socket socket, ServerModel model) {
        this.writer = new Writer(model, socket);
        this.reader = new Reader(model, socket);
        reader.setUsername(username);
        Thread rThread = new Thread((Runnable) reader);
        rThread.start();

    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public IWriter getWriter() {
        return writer;
    }


    public void sendMessage(Message m){
            writer.setOutMessage(m);
        }
    }
