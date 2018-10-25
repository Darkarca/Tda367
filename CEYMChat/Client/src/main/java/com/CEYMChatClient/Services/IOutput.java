package com.CEYMChatClient.Services;

import com.CEYMChatLib.Message;
import java.io.IOException;
import java.net.Socket;

public interface IOutput {

    void sendMessage(Message message) throws IOException;

    void connectToServer(String serverIP);

    void login(String userName);

    Socket getSocket();

    void stop();
}
