package com.CEYMChat;

import java.io.*;
import java.net.*;
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

public class ClientController extends Application {

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;


    @Override
    public void start(Stage primaryStage) {
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Type Here: "));
        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);
        BorderPane mainPane = new BorderPane();
        // Text area to display contents
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage


        new Thread(() -> {
            try {
                // Create a socket to connect to the server
                Socket socket = new Socket("localhost", 8000);

                // Create an input stream to receive data from the server
                fromServer = new DataInputStream(socket.getInputStream());

                // Create an output stream to send data to the server
                toServer = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    tf.setOnAction(e -> {
                        try {
                            // Get the text from the text field
                            String string = tf.getText().trim();
                            tf.setText("");

                            // Send the text to the server
                            toServer.writeUTF(string);
                            toServer.flush();

                            // Display to the text area
                            ta.appendText("Me : " + string + "\n");
                        } catch (IOException ex) {
                            System.err.println(ex);
                        }
                    });
                    // Get text from the server
                    String friend = fromServer.readUTF();

                    // Display input to the text area
                    Platform.runLater(() -> {
                        ta.appendText("Friend : " + friend + '\n');
                    });
                }
            }
            catch (IOException ex) {
                ta.appendText(ex.toString() + '\n');
            }
        }).start();
    }
}
