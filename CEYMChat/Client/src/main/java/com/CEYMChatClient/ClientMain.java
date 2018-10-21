package com.CEYMChatClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetAddress;

/** This class should do nothing else than to launch the client application. */
public class ClientMain extends Application {

    /** Runs the Client module as a main method */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ClientView.fxml"));
        primaryStage.setTitle("CEYMChatServer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println(InetAddress.getLocalHost().getHostAddress().toString());
    }
}