package com.CEYMChatClient;

import com.CEYMChatLib.Message;
import com.CEYMChatLib.UserDisplayInfo;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
public interface IController {

    /**
     * Called when a username has been chosen, notifies the
     * Server that someone has connected so that they can be
     * identified aswell as initiating the GUI
     */
    @FXML
    public void login() throws IOException;


    /**
     *  Sends the text in the chatBox to the Server
     *  together with whichever user you have chosen
     */
    @FXML
    public void sendString() throws IOException;


    /** Handles how the controller asks the Server for an updated list of active users */
    public void refreshFriendList();


    /** Handles how the controller displays a new message in the GUI */
    public void displayNewMessage(Message m);


    /** Handles how the controller creates a new userlist for the GUI */
    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException;


    /** Handles how the controller displays a new userlist in the GUI */
    public void showOnlineFriends (ArrayList<UserDisplayInfo> userDisplayInfos) throws IOException;


}
