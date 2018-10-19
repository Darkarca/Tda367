package com.CEYMChat.Model;

import com.CEYMChat.Message;
import com.CEYMChat.Services.IWriter;
import com.CEYMChat.Services.Writer;

import java.net.Socket;

public class User {
    private String username;
    private IWriter writer;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void initIO(Socket socket, ServerModel model){
        this.writer = new Writer(socket);
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


}
