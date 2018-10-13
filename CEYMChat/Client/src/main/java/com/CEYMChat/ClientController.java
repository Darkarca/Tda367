package com.CEYMChat;
import com.CEYMChat.Model.ClientModel;
import com.CEYMChat.Services.Connection;
import com.CEYMChat.Services.IService;
import com.CEYMChat.View.FriendListItem;
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

    private ClientModel model;

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
    private IService connection;




    /**
     * Captures input from user and send makes use of model to send message
     */

    public ClientModel getModel() {
        return model;
    }

    public void sendString() throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        connection.sendStringMessage(toSend, currentChat);   //Change sendToTextField.getText() to click on friend
        messageWindow.appendText("Me: "+toSend+"\n");
    }


    @FXML
    public void connectToServer(MouseEvent mouseEvent) {
        try{
            model = new ClientModel();
            URL url = Paths.get("Client/src/main/resources/View/login.fxml").toUri().toURL();
            login = FXMLLoader.load(url);
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.initStyle(StageStyle.UTILITY);
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(login));
            loginStage.show();

            toggleChatBox();
            connectButton.setDisable(true);

            connection = new Connection(this);
            new Thread(connection).start();
            System.out.println("Connection started");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void login(){
        try {
            connection.sendCommandMessage("setUser", loginTextField.getText());
            model.setUsername(loginTextField.getText());
            Window window = loginButton.getScene().getWindow();
            window.hide();

            //model.login();

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
            connection.sendCommandMessage("requestChat","user2");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException {
        System.out.println("new friend list updating 3");
        for (UserDisplayInfo uInfo : friendList) {
            System.out.println(uInfo.getUsername());
            if (!uInfo.getUsername().equals(model.getUsername())) {
                FriendListItem userItem = new FriendListItem(uInfo.getUsername());
                friendItemList.add(userItem);
                userItem.getFriendPane().setOnMouseClicked(Event -> {
                    currentChat = userItem.getFriendUsername().getText();
                    System.out.println("CurrentChat set to: " + currentChat);
                });
            }
        }
    }

    public void showOnlineFriends (ArrayList<UserDisplayInfo> friendList) throws IOException {
        friendItemList.clear();
        System.out.println("new friend list updating 2");
        createFriendListItemList(friendList);
        for (FriendListItem friendListItem : friendItemList) {
            friendsFlowPane.getChildren().add(friendListItem.getFriendPane());
        }
    }


}
