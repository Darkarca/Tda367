package com.CEYMChat;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ClientController {
    ClientModel model = ClientModel.getModelInstance();
    @FXML
    TextField textInput;
    @FXML
    TextArea chatWindow;

    // This is just a dummy method to illustrate how the model/controller might work together.
    public void displayNewMessage(){
        //String message = model.getNewMessage();
        //chatWindow.appendText(message);
    }
}
