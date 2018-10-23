package com.CEYMChatClient.View;

import com.CEYMChatClient.Controller.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class EmojiItem {


    ClientController clientController;

    @FXML
    private Label emojiCharLabel;
    @FXML
    private AnchorPane emojiPane;

    public EmojiItem (String emojiChar, ClientController clientController) {

        try {
            URL url = Paths.get("Client/src/main/resources/View/emojiItem.fxml").toUri().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.emojiCharLabel.setText(emojiChar);
        this.clientController = clientController;
    }

    public AnchorPane getEmojiPane() {
        return emojiPane;
    }

    @FXML
    public void onClick(){
        clientController.chatBoxAppendText(emojiCharLabel.getText());
    }
}
