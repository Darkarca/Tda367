package com.CEYMChat.Services;
import com.CEYMChat.CommandName;
import com.CEYMChat.Message;

import java.io.File;
import java.io.IOException;
/**
 * Interface for communicating with the server.
 */
public interface IService {

    public void sendCommandMessage(CommandName sCommand, String sData) throws IOException;
    public void setMessageOut(Message m) throws IOException;
    void sendStringMessage(String toSend, String currentChat) throws IOException;
    void read();
    public void connectToS();
    public void login(CommandName sCommand, String userName);


    void setFile(File selectedFile);
}

