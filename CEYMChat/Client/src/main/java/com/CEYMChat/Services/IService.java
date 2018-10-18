package com.CEYMChat.Services;
import com.CEYMChat.CommandName;
import com.CEYMChat.Message;

import java.io.File;
import java.io.IOException;
/**
 * Interface for communicating with the server.
 */
public interface IService {

    void setMessageOut(Message m) throws IOException;
    void read();
    void connectToS();
    void login(CommandName sCommand, String userName);
    void sendMessage(Message stringMessage) throws IOException;

    void stop();
}

