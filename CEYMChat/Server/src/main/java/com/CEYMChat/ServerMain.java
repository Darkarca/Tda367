package com.CEYMChat;

import javafx.application.Application;
import javafx.stage.Stage;

public class ServerMain extends Application {

    private ServerModel model = new ServerModel();
    private SocketHandler socketHandler = new SocketHandler(model);

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Server running");
        socketHandler.start();
    }
}