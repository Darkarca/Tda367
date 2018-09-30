package com.CEYMChat;


import com.CEYMChat.Model.ClientModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * This class should do nothing else than to launch the client application.
 */

public class ClientMain extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientModel model = ClientModel.getModelInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/CEYMChat/View/ClientView.fxml"));
        primaryStage.setTitle("CEYMChat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();




    }
}
