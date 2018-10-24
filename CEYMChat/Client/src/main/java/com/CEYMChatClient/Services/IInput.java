package com.CEYMChatClient.Services;

import com.CEYMChatLib.CommandName;
import com.CEYMChatLib.Message;
import java.io.IOException;
import java.net.Socket;

/** Interface for communicating with the server. */
public interface IInput {
    /** Handles how the service connects
     *  the IInput to the ServerSocket
     */
    void connectToS(Socket socket);

    void stop();
}

