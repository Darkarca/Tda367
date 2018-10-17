package com.CEYMChat;

public class ServerMain{

    public static void main(String[] args) throws Exception {
        System.out.println("Server running");
        ServerMain main = new ServerMain();
        main.startHandler();
    }

    private ServerModel model = new ServerModel();
    private SocketHandler socketHandler = new SocketHandler(model);
    public void startHandler(){
        socketHandler.start();
    }
}
