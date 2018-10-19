package com.CEYMChat;

import com.CEYMChat.Services.SocketHandler;
import com.CEYMChat.Model.ServerModel;

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
    public SocketHandler getHandler(){
        return socketHandler;
    }
}
