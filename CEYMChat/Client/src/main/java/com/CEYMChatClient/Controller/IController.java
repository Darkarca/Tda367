package com.CEYMChatClient.Controller;

import com.CEYMChatLib.Message;
import javafx.fxml.FXML;
import java.io.File;
import java.io.IOException;

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
     * @param message
     */
    void displayNewMessage(Message message) throws IOException;


    /**
     * Handles how the controller creates a new userlist for the GUI
     * @param friendList
     * @throws IOException
     */

    /**
     * Handles how the controller displays a new userlist in the GUI
     * @throws IOException
     */
    void showOnlineFriends() throws IOException;


    void chatBoxAppendText(String s);

    void connectionEnded();
}
