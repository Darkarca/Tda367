package com.CEYMChatClient.Services;

import java.net.Socket;

/** Interface for communicating with the server. */
public interface IInput {
    /** Handles how the service connects
     *  the IInput to the ServerSocket
     */
    void connectToServer(Socket socket);

    void stop();
}

