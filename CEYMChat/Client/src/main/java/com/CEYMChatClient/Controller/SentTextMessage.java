package com.CEYMChatClient.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Controller for textMessageSender.fxml
 * Creates a GUI element for a sent text message.
 */
public class SentTextMessage implements IFXMLController {

    @FXML
    public Label sMessageTextLabel;
    @FXML
    public AnchorPane sMessagePane;
    String sMessage;

    /**
     * constructor with initialized the sent message
     * @param sMessage the sent message
     * @throws IOException
     */
    public SentTextMessage(String sMessage) throws IOException {

        this.sMessage = sMessage;
        load();
        this.sMessageTextLabel.setText(sMessage);
    }

    @Override
    public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View/textMessageSender.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public AnchorPane getPane(){
        return sMessagePane;
    }
}
