package com.CEYMChat;

import com.CEYMChat.Services.IReader;
import com.CEYMChat.Services.IWriter;

import java.net.Socket;
import java.util.List;

public class User {
    private String username;
    private IReader reader;
    private IWriter writer;

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void startThreads(Socket socket, ServerModel model){
        this.writer = new Writer(socket);
        this.reader = new Reader(model, socket);
        Thread rThread = new Thread((Runnable) reader);
        rThread.start();
    }

    public void sendMessage(Message m){
            writer.setOutMessage(m);
        }

    public void sendUserList(List<User> userList) {
        writer.sendUserList(userList);
    }
}
