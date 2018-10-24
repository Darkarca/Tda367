package com.CEYMChatClient.Services;

import com.CEYMChatLib.CommandName;
import com.CEYMChatLib.Message;

import java.io.IOException;
import java.net.Socket;

public interface IOutput {
    void sendMessage(Message message) throws IOException;

    void connectToS();

    void login(CommandName setUser, String userName);

    Socket getSocket();
}
