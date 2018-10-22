package com.CEYMChatClient.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import sun.font.TextLabel;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Creates a GUI element for a sent text message.
 */
public class SentTextMessage {

    @FXML
    public Label SmessageTextLabel;
    @FXML
    public AnchorPane SmessagePane;

    /**
     * constructor with initialized the sent message
     * @param sMessage the sent message
     * @throws IOException
     */
    public SentTextMessage( String sMessage) throws IOException {

        URL url = Paths.get("Client/src/main/resources/View/textMessageSender.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.SmessageTextLabel.setText(sMessage);
    }

}
