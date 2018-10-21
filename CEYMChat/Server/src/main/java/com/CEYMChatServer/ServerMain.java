package com.CEYMChatServer;

import com.CEYMChatServer.Services.SocketHandler;
import com.CEYMChatServer.Model.ServerModel;

/**
 * Class that starts the server
 */
public class ServerMain{
    private ServerModel model = new ServerModel();
    private SocketHandler socketHandler = new SocketHandler(model);
    public void startHandler(){
        socketHandler.start();
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Server running");
        ServerMain main = new ServerMain();
        main.startHandler();
    }
}
