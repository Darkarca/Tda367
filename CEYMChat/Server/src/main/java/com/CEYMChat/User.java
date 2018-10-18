package com.CEYMChat;

import com.CEYMChat.Services.IReader;
import com.CEYMChat.Services.IWriter;
import java.net.Socket;

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
    public void initThreads(Socket socket, ServerModel model){
        this.writer = new Writer(socket);
        this.reader = new Reader(model, socket);
    }
    public void sendMessage(Message m){
            writer.setOutMessage(m);
        }
    public void sendInfo(Message m){
        writer.setOutMessage(m);
    }

    public IWriter getWriter() {
        return writer;
    }
    public void startThreads(){
        Thread rThread = new Thread((Runnable) reader);
        rThread.start();
    }
}
