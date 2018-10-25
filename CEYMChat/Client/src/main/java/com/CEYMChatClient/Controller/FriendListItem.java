package com.CEYMChatClient.Controller;

import com.CEYMChatLib.UserDisplayInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.IOException;

/**
 * Controller for friendListItem.fxml
 * Creates and handles a GUI-element representing a friend.
 */
public class FriendListItem implements IFXMLController {

    private UserDisplayInfo uInfo = new UserDisplayInfo();

    @FXML
    private AnchorPane friendPane;
    @FXML
    private Label friendUsername;
    @FXML
    private Circle onlineIndicator;
    @FXML
    private ImageView friendImg;
    @FXML
    private ImageView friendIndicator;

    /**  Getters and setters **/
    public Label getFriendUsername() {
        return friendUsername;
    }
    public AnchorPane getPane(){return friendPane; }
    public void setUInfo(UserDisplayInfo uInfo){
        this.uInfo = uInfo;
    }
    public UserDisplayInfo getUInfo(){
        return uInfo;
    }

    /** Constructor creating the friendlistitem for the controller to show */
    public FriendListItem(UserDisplayInfo uInfo) throws IOException {
        load();
        this.friendUsername.setText(uInfo.getUsername());
        if(uInfo.getOnline()) {
            this.onlineIndicator.setFill(Color.GREEN);
        }
        else{
            this.onlineIndicator.setFill(Color.RED);
        }
    }

    /** Is called when you press the friendIndicator image */
    @FXML
    public void toggleFriend(){
        uInfo.setIsFriend(!uInfo.getIsFriend());
        if(uInfo.getIsFriend()) {
            friendIndicator.setImage(new Image("friend.png"));
        }else if(!uInfo.getIsFriend()){
            friendIndicator.setImage(new Image("notFriend.png"));
        }
    }

    /** correctly sets the image of friendIndicator
     * incase of a forced change in the isFriend variable
     */
    public void setFriend() {
        if(uInfo.getIsFriend()) {
            friendIndicator.setImage(new Image("friend.png"));
        }else if(!uInfo.getIsFriend()){
            friendIndicator.setImage(new Image("notFriend.png"));
        }
    }


    @Override
    public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View/friendListItem.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
