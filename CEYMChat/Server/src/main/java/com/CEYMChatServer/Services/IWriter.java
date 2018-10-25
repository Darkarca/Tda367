package com.CEYMChatServer.Services;

import com.CEYMChatLib.IObservable;
import com.CEYMChatLib.Message;
import java.net.Socket;
/**
 * Interface for classes intending to write objects to an outputStream
  */
public interface IWriter {


    /** Getters and setters **/
    Message getOutMessage();
    Socket getSocket();


    /** Setting the message on outputstream of the socket */
    void setOutMessage(Message outMessage);


    /** Handles how the writer writes objects to its outputStream */
    void writeToStream();


}
