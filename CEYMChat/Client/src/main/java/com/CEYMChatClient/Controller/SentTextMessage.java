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
 * Creates a GUI element for a sent text message.
 */
public class SentTextMessage implements IFXMLViewController {

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
        URL url = null;
        try {
            url = Paths.get("Client/src/main/resources/View/textMessageSender.fxml").toUri().toURL();
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
        return sMessagePane;
    }
}
