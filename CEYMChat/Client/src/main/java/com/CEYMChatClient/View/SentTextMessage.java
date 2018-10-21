package com.CEYMChatClient.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import com.CEYMChatLib.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Creates a GUI element for friends.
 */
public class SentTextMessage {


    @FXML
    public Label SmessageTextLabel;
    @FXML
    public AnchorPane SmessagePane;


    public SentTextMessage( String sMessage) throws IOException {

        URL url = Paths.get("Client/src/main/resources/View/textMessageSender.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.SmessageTextLabel.setText(sMessage);
    }

    public AnchorPane getSmessagePane() {
        return SmessagePane;
    }
}
