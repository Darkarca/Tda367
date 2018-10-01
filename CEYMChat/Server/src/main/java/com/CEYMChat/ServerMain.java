package com.CEYMChat;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain extends Application {

    ServerModel model = new ServerModel();
    SocketHandler socketHandler = new SocketHandler(model);

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Server running");
        socketHandler.start();
        /*while (true) {
            while (model.getMessage() != null) {
                System.out.println(model.getMessage().getData().toString());
            }
           // System.out.print("Hello world!");
        }*/
    }
}