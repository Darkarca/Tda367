package com.CEYMChat;

import com.CEYMChat.Services.SocketHandler;
<<<<<<< HEAD
import com.CEYMChat.Model.*;
=======
import com.CEYMChat.Model.ServerModel;
>>>>>>> c744fa3f9fa53e1cb5b976661f9d5ec75041f702

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
