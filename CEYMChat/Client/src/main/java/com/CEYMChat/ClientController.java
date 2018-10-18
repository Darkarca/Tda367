package com.CEYMChat;
import com.CEYMChat.Model.ClientModel;
import com.CEYMChat.Services.IService;
import com.CEYMChat.Services.Services;
import com.CEYMChat.View.FriendListItem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * Controller for the Client.
 */

public class ClientController implements IController{
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
    private Text currentChat;
    @FXML
    private TextArea messageWindow;
    @FXML
    private TextField sendToTextField;
    @FXML
    private FlowPane friendsFlowPane;
    private Parent login;
    private Stage loginStage;
    private ArrayList<FriendListItem> friendItemList = new ArrayList<>();
    String currentChatName;
    public IService service =new Services(model,this);
    String user;
    /**
     * Captures input from user and send makes use of model to send message
     */

    public ClientController(){
        loginStage = new Stage();
    }
    public ClientModel getModel() {
        return model;
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

    public void connectToServer(MouseEvent mouseEvent) {
        try{
            URL url = Paths.get("Client/src/main/resources/View/login.fxml").toUri().toURL();
            login = FXMLLoader.load(url);
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.initStyle(StageStyle.UTILITY);
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(login));
            loginStage.show();
            service.connectToS();
            toggleChatBox();
            connectButton.setDisable(true);
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void login(){
            service.login(CommandName.SET_USER, loginTextField.getText());
            model.setUsername(loginTextField.getText());
            Window window = loginButton.getScene().getWindow();
            window.hide();
            user = loginTextField.getText();
    }

    @FXML
    public void refreshFriendList(){
        try {
            System.out.println("Send refreshFriendList command");
            service.sendCommandMessage(CommandName.REFRESH_FRIENDLIST,user);
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
