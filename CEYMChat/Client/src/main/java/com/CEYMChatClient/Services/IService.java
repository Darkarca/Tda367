package com.CEYMChatClient.Services;

import com.CEYMChatLib.CommandName;
import com.CEYMChatLib.Message;
import java.io.IOException;
/** Interface for communicating with the server. */
public interface IService {

    /** Handles how the service sends a message to the Server */
    void setMessageOut(Message m) throws IOException;

    /** Handles how the service starts a Thread to read notifications, messages etc from the Server */
    void read();

    /** Handles how the service connects the IService to the ServerSocket */
    void connectToS();

    /** Handles how the service sends a command to the server letting it know a connection has been made so the Server can identify the user */
    void login(CommandName sCommand, String userName);

    /** Handles how the service sends a message to the Server */
    void sendMessage(Message stringMessage) throws IOException;

    /** Handles how the service shuts off the connection between the service and the Server safely */
    void stop();

}

