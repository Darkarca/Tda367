package com.CEYMChat.Services;

import com.CEYMChat.Message;
import java.net.Socket;
        // Interface for classes intending to write objects to an outputStream
public interface IWriter {


    /**
    *  Getters and setters
    **/
    Message getOutMessage();
    Socket getSocket();


    /**
    * Setting the message on outputstream of the socket
    * @param m
    */
    void setOutMessage(Message m);


    /**
    * Handles how the writer writes objects to its outputStream
    */
    void writeToStream();


}
