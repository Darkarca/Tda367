package Model;
import java.io.*;
import java.net.*;
import java.util.Date;

import Service.ClientHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

    public class Server extends Application {

        private int numOfSession = 1;
        @Override // Override the start method in the Application class
        public void start(Stage primaryStage) {



            BorderPane mainPane = new BorderPane();
            // Text area to display contents
            TextArea log = new TextArea();
            mainPane.setCenter(new ScrollPane(log));
            //mainPane.setTop(paneForTextField);

            // Create a scene and place it in the stage
            Scene scene = new Scene(mainPane, 450, 200);
            primaryStage.setTitle("Server"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage

            new Thread(() -> {
                try {
                    // Create a server socket
                    ServerSocket serverSocket = new ServerSocket(8000);
                    //the server accepts connections from the clients to make sessions
                    while (true) {
                        // Listen for a connection request from the first client
                        Socket socket1 = serverSocket.accept();
                        //update the log
                        Platform.runLater(() -> {
                            log.appendText(new Date() + ": Client 1 joined session number " + numOfSession + '\n');
                            log.appendText("Client 1's IP address" + socket1.getInetAddress().getHostAddress() + '\n');
                        });
                        // Listen for a connection request from the second client
                        Socket socket2 = serverSocket.accept();

                        //update the log
                        Platform.runLater(() -> {
                            log.appendText(new Date() + ": Client 2 joined session number " + numOfSession + '\n');
                            log.appendText("Client 2's IP address" + socket2.getInetAddress().getHostAddress() + '\n');
                        });
                        Platform.runLater(() -> {
                            log.appendText("Session " + numOfSession++ + " is started " + '\n');
                        });
                        Runnable clienth1 = new ClientHandler(socket1,socket2);
                        Thread thread1 = new Thread(clienth1);
                        thread1.start();
                        Runnable clienth2 = new ClientHandler(socket2,socket1);
                        Thread thread2 = new Thread(clienth2);
                        thread2.start();

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();


        }
    }

