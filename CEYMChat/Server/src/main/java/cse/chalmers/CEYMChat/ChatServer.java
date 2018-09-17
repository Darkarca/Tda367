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
                        textLog.appendText(new Date() + ": Client 1 joined session " + numOfSession + '\n');
                        textLog.appendText("Client 1's IP address" + socket1.getInetAddress().getHostAddress() + '\n');
                    });
                    // Listen for a connection request from the second client
                    Socket socket2 = serverSocket.accept();

                    //update the log
                    Platform.runLater(() -> {
                        textLog.appendText(new Date() + ": Client 2 joined session " + numOfSession + '\n');
                        textLog.appendText("Client 2's IP address" + socket2.getInetAddress().getHostAddress() + '\n');
                    });
                    Platform.runLater(() -> {
                        textLog.appendText("Session " + numOfSession++ + " is started " + '\n');
                    });

                    //start a thread for two clients
                    Runnable session = new Session(socket1,socket2);
                    Thread thread = new Thread(session);
                    thread.start();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}