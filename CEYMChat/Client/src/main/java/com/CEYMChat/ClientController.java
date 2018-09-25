package com.CEYMChat;
import com.CEYMChat.Model.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ClientController {
    ClientModel model = ClientModel.getModelInstance();

    @FXML
    TextField textInput;

    @FXML
    TextArea chatWindow;

    @FXML
    Button sendButton;

    // This is just a dummy method to illustrate how the model/controller might work together.
    public void displayNewMessage(){
        //String message = model.getNewMessage();
        //chatWindow.appendText(message);
    }

    /**
     * Captures input from user and send makes use of model to send message
     */
    public void sendString(){
        String toSend = textInput.getText();
        model.sendStringMessage(toSend);

    }
}
