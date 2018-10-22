package com.CEYMChatClient.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Creates a GUI element for friends.
 */
public class SentTextMessage {


    @FXML
    public Label sMessageTextLabel;
    @FXML
    public AnchorPane sMessagePane;


    public SentTextMessage( String sMessage) throws IOException {

        URL url = Paths.get("Client/src/main/resources/View/textMessageSender.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.sMessageTextLabel.setText(sMessage);
    }

    public AnchorPane getsMessagePane() {
        return sMessagePane;
    }
}
