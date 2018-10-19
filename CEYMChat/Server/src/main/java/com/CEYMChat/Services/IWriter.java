package com.CEYMChat.Services;

import com.CEYMChat.Message;
import java.net.Socket;
        // Interface for classes intending to write objects to an outputStream
public interface IWriter {

    void writeToStream();       // Handles how the writer writes objects to its outputStream
    /** Getters and setters **/
    Message getOutMessage();
    void setOutMessage(Message m);
    Socket getSocket();
}
