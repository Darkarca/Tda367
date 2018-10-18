package com.CEYMChat.Services;

import com.CEYMChat.Message;

import java.net.Socket;

public interface IWriter {

    void setOutMessage(Message m);
    void writeToStream();
    Message getOutMessage();

    Socket getSocket();
}
