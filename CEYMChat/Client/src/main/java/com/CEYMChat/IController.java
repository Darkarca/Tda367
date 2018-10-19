package com.CEYMChat;

import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
public interface IController {
    public void sendString() throws IOException;
    @FXML
    public void login() throws IOException;
    @FXML
    public void refreshFriendList();                // Handles how the controller asks the Server for an updated list of active users
    public void displayNewMessage(Message m);        // Handles how the controller displays a new message in the GUI
    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException;    // Handles how the controller creates a new userlist for the GUI
    public void showOnlineFriends (ArrayList<UserDisplayInfo> userDisplayInfos) throws IOException;     // Handles how the controller displays a new userlist in the GUI


}
