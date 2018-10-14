package com.CEYMChat.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import java.io.IOException;

/**
 * Creates a GUI element for friends.
 */
public class FriendListItem {

    @FXML
    private AnchorPane friendPane;

    @FXML
    private Label friendUsername;

    @FXML
    private Circle onlineIndicator;
    @FXML
    private ImageView friendImg;
    public FriendListItem(String username) throws IOException {

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

    public Label getFriendUsername() {
        return friendUsername;
    }

    public AnchorPane getFriendPane() {
        return friendPane;
    }

}
