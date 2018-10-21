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
public class RecivedTextMessage {



    @FXML
    public AnchorPane RmessagePane;
    @FXML
    public Label RmessageTextLabel;



    public RecivedTextMessage(String rMessage) throws IOException {

        URL url = Paths.get("View/textMessageReciever.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.load((getClass().getClassLoader().getResource("View/textMessageReciever.fxml")));

        this.RmessageTextLabel.setText(rMessage);
    }

    public AnchorPane getRmessagePane() {
        return RmessagePane;
    }
}
