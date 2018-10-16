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

<<<<<<< HEAD
=======
    public void startThreads(Socket socket, ServerModel model){
        this.writer = new Writer(socket);
        this.reader = new Reader(model, socket);
        Thread rThread = new Thread((Runnable) reader);
        rThread.start();
    }

>>>>>>> c90053e28ad368ed314e77d2ca777d5c92511f0e
    public void sendMessage(Message m){
            writer.setOutMessage(m);
        }

    public void sendUserList(List<User> userList) {
        writer.sendUserList(userList);
    }
}
