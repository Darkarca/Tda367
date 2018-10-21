package com.CEYMChatClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.CEYMChatLib.*;

/** This class should do nothing else than to launch the client application. */
public class ClientMain extends Application {

    /** Runs the Client module as a main method */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ClientView.fxml"));
        primaryStage.setTitle("CEYMChat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}