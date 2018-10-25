package com.CEYMChatClient.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Creates a GUI element for a received text message.
 */
public class ReceivedTextMessage implements IFXMLController {

    @FXML
    public AnchorPane rMessagePane;
    @FXML
    public Label rMessageTextLabel;
    String rMessage;

    /**
     * constructor with initialized the received message
     * @param rMessage the received message
     * @throws IOException
     */
    public ReceivedTextMessage(String rMessage) throws IOException {

        this.rMessage = rMessage;
        load();
        this.rMessageTextLabel.setText(rMessage);
    }

    @Override
    public void load() {
        URL url = null;
        try {
            url = Paths.get("Client/src/main/resources/View/textMessageReciever.fxml").toUri().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public AnchorPane getPane(){
        return rMessagePane;
    }
}
