package com.CEYMChat;
import com.CEYMChat.Model.ClientModel;
import com.CEYMChat.Services.IService;
import com.CEYMChat.Services.Services;
import com.CEYMChat.View.FriendListItem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * Controller for the Client.
 */

public class ClientController implements IController{

    ClientModel model;
    public IService service;

    @FXML
    AnchorPane loginPane;
    @FXML
    AnchorPane mainPane;
    @FXML
    StackPane programStackPane;
    @FXML
    private Button sendButton;
    @FXML
    private Button connectButton;
    @FXML
    private TextField chatBox;
    @FXML
    private Text currentChat;
    @FXML
    private TextArea messageWindow;
    @FXML
    private TextField sendToTextField;
    @FXML
    private FlowPane friendsFlowPane;
    @FXML
    private TextField loginTextField;
    @FXML
    private Button loginButton;

    private ArrayList<FriendListItem> friendItemList = new ArrayList<>();

    String currentChatName;
    String userName;

    /**
     * Captures input from user and send makes use of model to send message
     */

    @FXML
    public void onClick(){
        this.userName = loginTextField.getText();
        login();
        mainPane.toFront();
        toggleChatBox();
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }


    public void appInit(){
        model = new ClientModel();
        service =new Services(model,this);
    }

    public void login(){
        appInit();
        service.connectToS();
        service.login(CommandName.SET_USER, userName);
        model.setUsername(userName);

    }

    Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            refreshFriendList();//this method only works first time at the moment. some bug.
            System.out.println("this is called every 2 seconds on UI thread");
        }
    }));

    public void sendString() throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        service.sendStringMessage(toSend, currentChatName);   //Change sendToTextField.getText() to click on friend
        messageWindow.appendText("Me: "+toSend+"\n");
    }


    @FXML
    public void refreshFriendList(){
        try {
            System.out.println("Send refreshFriendList command");
            service.sendCommandMessage(CommandName.REFRESH_FRIENDLIST,userName);
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
            service.sendCommandMessage(CommandName.REQUEST_CHAT,"user2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFriendListItemList (ArrayList<UserDisplayInfo> friendList) throws IOException {
        System.out.println("New list of friendItems created");
        for (UserDisplayInfo uInfo : friendList) {
            System.out.println("User added: " + uInfo.getUsername());
            if (!uInfo.getUsername().equals(model.getUsername())) {
                FriendListItem userItem = new FriendListItem(uInfo.getUsername());
                friendItemList.add(userItem);
                userItem.getFriendPane().setOnMouseClicked(Event -> {
                    currentChatName = userItem.getFriendUsername().getText();
                    currentChat.setText("Currently chatting with: " + currentChatName);
                    System.out.println("CurrentChat set to: " + currentChatName);
                });
            }
        }
    }

    public void showOnlineFriends (ArrayList<UserDisplayInfo> friendList) throws IOException {
        friendItemList.clear();
        System.out.println("FriendListItems are being created");
        createFriendListItemList(friendList);
        friendsFlowPane.getChildren().clear();
        for (FriendListItem friendListItem : friendItemList) {
            friendsFlowPane.getChildren().add(friendListItem.getFriendPane());
        }
    }
}
