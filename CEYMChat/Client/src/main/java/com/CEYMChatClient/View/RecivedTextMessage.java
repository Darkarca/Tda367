package com.CEYMChatClient.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Creates a GUI element for a received text message.
 */
public class RecivedTextMessage {

    @FXML
    public AnchorPane RmessagePane;
    @FXML
    public Label RmessageTextLabel;

    /**
     * constructor with initialized the received message
     * @param rMessage the received message
     * @throws IOException
     */
    public RecivedTextMessage(String rMessage) throws IOException {

        URL url = Paths.get("Client/src/main/resources/View/textMessageReciever.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.RmessageTextLabel.setText(rMessage);
    }

}
