package com.CEYMChat.View;

import com.CEYMChat.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sun.font.TextLabel;

import java.io.IOException;
import java.net.MalformedURLException;
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

        URL url = Paths.get("Client/src/main/resources/View/textMessageReciever.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.RmessageTextLabel.setText(rMessage);
    }

    public AnchorPane getRmessagePane() {
        return RmessagePane;
    }
}
