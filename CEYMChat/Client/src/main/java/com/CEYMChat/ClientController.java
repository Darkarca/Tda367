package com.CEYMChat;
import com.CEYMChat.Model.ClientModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ClientController {
    ClientModel model = ClientModel.getModelInstance();

    @FXML
    private Button sendButton;
    @FXML
    private TextField loginTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button connectButton;
    @FXML
    private TextField chatBox;
    @FXML
    private TextArea messageWindow;
    @FXML
    private TextField sendToTextField;
    @FXML
    private FlowPane friendsFlowPane;
    private Parent login;
    private Stage loginStage = new Stage();
    private ArrayList<FriendListItem> friendItemList = new ArrayList<>();
    String currentChat;

    /**
     * Captures input from user and send makes use of model to send message
     */

    public void sendString() throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        model.sendStringMessage(toSend, currentChat);   //Change sendToTextField.getText() to click on friend
        messageWindow.appendText("Me: "+toSend+"\n");
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
            model.connectToServer();
            model.setController(this);
            toggleChatBox();
            connectButton.setDisable(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void login(){
        try {
            model.sendCommandMessage("setUser", loginTextField.getText());
            model.setUsername(loginTextField.getText());
            Window window = loginButton.getScene().getWindow();
            window.hide();
            model.login();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void toggleChatBox(){
        if (chatBox.isEditable())
            chatBox.setEditable(false);
        else{
            chatBox.setEditable(true);
        }
    }

    public void displayNewMessage(String s) {
        System.out.println("displayNewMessage has been called with string: " + s);
        messageWindow.appendText(s + "\n");
    }

    public void requestChat(){
        try {
            model.sendCommandMessage("requestChat","user2");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException {
        for (UserDisplayInfo uInfo : friendList) {
            FriendListItem userItem = new FriendListItem(uInfo.getUsername());
            friendItemList.add(userItem);
            userItem.getFriendPane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent Event){
                    currentChat = userItem.getFriendUsername().getText();
                    System.out.println("CurrentChat set to: " + currentChat);
                }
            });
        }
    }

    public void showOnlineFriends (ArrayList<UserDisplayInfo> friendList) throws IOException {
        friendItemList.clear();
        createFriendListItemList(friendList);
        for (FriendListItem friendListItem : friendItemList) {
            friendsFlowPane.getChildren().add(friendListItem.getFriendPane());
        }
    }


}
