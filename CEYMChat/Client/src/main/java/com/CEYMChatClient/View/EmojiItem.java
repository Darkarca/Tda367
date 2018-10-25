package com.CEYMChatClient.View;

import com.CEYMChatClient.Controller.ClientController;
import com.CEYMChatClient.Controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * emojiItem is a controller to emojiItem fxml file
 */
public class EmojiItem implements IFXMLViewController {

    private IController clientController;

    @FXML
    private Label emojiCharLabel;
    @FXML
    private AnchorPane emojiPane;

    public EmojiItem (String emojiChar, ClientController clientController) {
        load();
        this.emojiCharLabel.setText(emojiChar);
        this.clientController = clientController;
    }

    public AnchorPane getPane() {
        return emojiPane;
    }

    /**
     * handles click event on an emoji. Sets the clicked emoji in the text box
     */
    @FXML
    public void onClick(){
        clientController.chatBoxAppendText(emojiCharLabel.getText());
    }

    @Override
    public void load() {
        try {
            URL url = Paths.get("Client/src/main/resources/View/emojiItem.fxml").toUri().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
