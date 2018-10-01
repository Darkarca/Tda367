package com.CEYMChat;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;

/**
 * This class should do nothing else than to launch the client application.
 */

public class ClientMain extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Paths.get("Client/src/main/resources/View/ClientView.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url); root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ClientView.fxml"));
        primaryStage.setTitle("CEYMChat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



    }
}
