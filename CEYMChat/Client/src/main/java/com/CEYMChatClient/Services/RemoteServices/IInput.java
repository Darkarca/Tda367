package com.CEYMChatClient.Services.RemoteServices;

/** Interface for communicating with the server. */
public interface IInput {
    /** Handles how the service connects
     *  the IInput to the ServerSocket
     */
    void connectToServer();


    void disconnect();
}

