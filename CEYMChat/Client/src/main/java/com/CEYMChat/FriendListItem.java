package com.CEYMChat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;


public class FriendListItem {

    @FXML
    AnchorPane friendPane;
    @FXML
    Label friendUsername;
    @FXML
    Circle onlineIndicator;
    @FXML
    ImageView friendImg;


    FriendListItem(String username) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View/friendListItem.fxml"));
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.friendUsername.setText(username);
    }

    @FXML
    public void selectFriend (){

    }

}
