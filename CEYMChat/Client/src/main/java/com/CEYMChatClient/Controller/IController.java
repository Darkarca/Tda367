package com.CEYMChatClient.Controller;

import com.CEYMChatLib.Message;
import com.CEYMChatLib.UserDisplayInfo;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.List;

public interface IController {

    /**
     * Called when a username has been chosen, notifies the
     * Server that someone has connected so that they can be
     * identified aswell as initiating the GUI
     * @throws IOException
     */
    @FXML
    void login() throws IOException;


    /**
     *  Sends the text in the chatBox to the Server
     *  together with whichever user you have chosen
     * @throws IOException
     */
    @FXML
    void sendString() throws IOException;

    /**
     * Handles how the controller displays a new message in the GUI
     * @param m
     */
    void displayNewMessage(Message m) throws IOException;


    /**
     * Handles how the controller creates a new userlist for the GUI
     * @param friendList
     * @throws IOException
     */
    void createFriendListItemList(List<UserDisplayInfo> friendList) throws IOException;


    /**
     * Handles how the controller displays a new userlist in the GUI
     * @param userDisplayInfos
     * @throws IOException
     */
    void showOnlineFriends(List<UserDisplayInfo> userDisplayInfos) throws IOException;


}
