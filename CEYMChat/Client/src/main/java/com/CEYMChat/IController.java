package com.CEYMChat;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
public interface IController {
    public void sendString() throws IOException;
    public void connectToServer(MouseEvent mouseEvent);
    @FXML
    public void login();
    @FXML
    public void refreshFriendList();
    @FXML
    public void toggleChatBox();
    public void displayNewMessage(String s);
    public void requestChat();
    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException;
    public void showOnlineFriends (ArrayList<UserDisplayInfo> userDisplayInfos) throws IOException;


}
