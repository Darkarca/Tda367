package cse.chalmers.CEYMChat;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ChatServer extends Application {

    private int numOfSession = 1;
    @Override
    public void start(Stage primaryStage) {
        TextArea textLog = new TextArea();
        Scene scene = new Scene(new ScrollPane(textLog), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();


        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                //the server accepts connections from the clients to make sessions
                while(true){
                    // Listen for a connection request from the first client
                    Socket socket1 = serverSocket.accept();
                    //update the log
                    Platform.runLater(() -> {
                        textLog.appendText(new Date() + ": A client has joined" + '\n');
                    });

                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}