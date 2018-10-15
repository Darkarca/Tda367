package com.CEYMChat.Services;

import com.CEYMChat.ClientController;
import com.CEYMChat.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * Interface for communicating with the server.
 */
public interface IService {

    public void sendCommandMessage(String sCommand, String sData) throws IOException;
    public void setMessageOut(Message m) throws IOException;


    void sendStringMessage(String toSend, String currentChat) throws IOException;
  //  void setUpConnection(ClientController c);



}
