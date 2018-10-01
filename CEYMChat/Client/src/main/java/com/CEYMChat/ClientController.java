package com.CEYMChat;
import com.CEYMChat.Model.ClientModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;


public class ClientController {
    ClientModel model = ClientModel.getModelInstance();

    @FXML
    TextField chatBox;

    @FXML
    TextArea chatWindow;

    @FXML
    Button sendButton;
    @FXML
    TextField loginTextField;


    Parent login;
    Stage loginStage = new Stage();



    // This is just a dummy method to illustrate how the model/controller might work together.
    public void displayNewMessage(){
        //String message = model.getNewMessage();
        //chatWindow.appendText(message);
    }

    /**
     * Captures input from user and send makes use of model to send message
     */
    public void sendString() throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        model.sendStringMessage(toSend);

    }

    public void connectToServer(MouseEvent mouseEvent) {
        try{
            URL url = Paths.get("Client/src/main/resources/View/login.fxml").toUri().toURL();
            login = FXMLLoader.load(url);
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.initStyle(StageStyle.UTILITY);
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(login));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        model.connectToServer();
    }
    public void login(){
        try {
            model.sendStringMessage(loginTextField.getText());
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        loginStage.hide();



    }
}
