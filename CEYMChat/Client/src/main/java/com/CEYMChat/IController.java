package com.CEYMChat;

import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
public interface IController {
    public void sendString() throws IOException;
    @FXML
    public void login() throws IOException;
    @FXML
    public void refreshFriendList();
    @FXML
    public void toggleChatBox();
    public void displayNewMessage(String s);
    public void requestChat();
    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException;
    public void showOnlineFriends (ArrayList<UserDisplayInfo> userDisplayInfos) throws IOException;


}
