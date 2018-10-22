package com.CEYMChatClient.Controller;


import com.CEYMChatClient.View.ReceivedTestMessage;
import com.CEYMChatClient.View.SentTextMessage;
import javafx.application.Platform;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.IService;
import com.CEYMChatClient.View.FriendListItem;
import com.CEYMChatLib.*;
import com.CEYMChatClient.Services.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Client and ClientMain .
 */
public class ClientController implements IController {

    private ClientModel model;
    private IService service;
    private List<FriendListItem> friendItemList = new ArrayList<>();
    private String currentChatName;
    private String userName;

    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private StackPane programStackPane;
    @FXML
    private Button sendButton;
    @FXML
    private Button connectButton;
    @FXML
    private TextField chatBox;
    @FXML
    private Text currentChat;
    @FXML
    private FlowPane chatPane;
    @FXML
    private TextField sendToTextField;
    @FXML
    private FlowPane friendsFlowPane;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button fileSend;
    @FXML
    private TextField ipField;
    @FXML
    private Button recordStopButton;
    @FXML
    private Button recordPlayButton;
    @FXML
    private Text fileName;
    @FXML
    private ImageView emojis;
    @FXML
    private FlowPane emojisFlowPane;
    @FXML
    private AnchorPane emojiPane;
    @FXML
    private Label emojiCharLabel;



    private List<UserDisplayInfo> friendList = new ArrayList<>();

    /** FXML methods**/
    @FXML
    public void onClick() throws IOException {
        this.userName = userNameTextField.getText();
        login();
        mainPane.toFront();
    }

    /**
     * Getters and Setters
     **/
    public IService getService() {
        return service;
    }


    /**
     *  Initiates the GUI
     */
    public void appInit() {
        model = new ClientModel();
        service = new Service(model, this);
        mainPane.getScene().getWindow().setOnCloseRequest(Event -> {    // Makes sure the client sends a notification to the Server that it has disconnected if the client is terminated
            try {
                saveMessages();
                service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.DISCONNECT, userName), userName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        File received = new File("Client/messages/received.csv");
        File sent = new File("Client/messages/received.csv");
        if (received.exists() && sent.exists()) {
            try {
                loadSavedMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when a username has been chosen, notifies the
     * Server that someone has connected so that they can be
     * identified aswell as initiating the GUI
     * @throws IOException
     */
    public void login() throws IOException {
        appInit();
        service.connectToS();
        service.login(CommandName.SET_USER, userName);
        model.setUsername(userName);
        model.setServerIP(ipField.getText());
    }


    /**
     * Sends the text in the chatBox to the Server
     * together with whichever user you have chosen
     * @throws IOException
     */
    public void sendString() throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        service.sendMessage(MessageFactory.createStringMessage(toSend, userName, currentChatName));
        createAddSendMessagePane("Me: " + toSend );
    }

    /**
     * creates a new Message AnchorPane and adds it to the chat flow pane
     * as a Send message
     * @param sMessage the STRING which will be sent
     */
    public void createAddSendMessagePane (String sMessage) throws IOException {
        SentTextMessage sentTextMessage = new SentTextMessage(sMessage);
        Platform.runLater(() -> chatPane.getChildren().add(sentTextMessage.sMessagePane));
    }

    /**
     * creates a new Message AnchorPane and adds it to the chat flow pane
     * as a received message
     * @param rMessage the STRING which will be received
     */
    public void createAddReceiveMessagePane (String rMessage) throws IOException {
        ReceivedTestMessage receivedTextMessage = new ReceivedTestMessage(rMessage);
        Platform.runLater(() -> chatPane.getChildren().add(receivedTextMessage.rMessagePane));
    }

    /**
     * Asks the Server for an updated active
     * userlist, called when the Refresh button is pressed
     */
    @FXML
    public void refreshFriendList() {
        try {
            service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.REFRESH_FRIENDLIST, userName), userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Checks which users have been tagged as friends
     *  and notifies the Server if any new friends have been added
     * @throws IOException
     */
    public void checkFriends() throws IOException {
        int changes = 0;
        for (UserDisplayInfo friendInfo : friendList) {         // Removes friends that have been deselected
            if (!friendInfo.getIsFriend() && !friendList.isEmpty() && friendList.contains(friendInfo)) {
                    friendList.remove(friendInfo);
                    changes++;
            }
        }
        for (FriendListItem fL : friendItemList) {              // Adds all newly selected friends
            Boolean add = true;
            if (fL.getUInfo().getIsFriend()) {
                for (UserDisplayInfo friendInfo : friendList) {
                    if (friendInfo.getUsername() == fL.getUInfo().getUsername()) {
                        add = false;
                    }
                }
                if (add) {
                    friendList.add(fL.getUInfo());
                    changes++;
                }
            }
        }
        if (changes != 0) {                                     // Notifies the Server if any changes have been made to the friends list
            service.sendMessage(MessageFactory.createFriendInfoList(friendList, userName, userName));
            return;
        }
    }


    /**
     * Currently unused, sends a command to the Server
     * to notify it that the user wants to initiate a
     * chat with someone, currently a message contains
     * a STRING with the username of the intended receiver instead
     */
    public void requestChat() {
        try {
            service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.REQUEST_CHAT, "user2"), userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the GUI with text from a new message
     * @param m
     */
    public void displayNewMessage(Message m) throws IOException {
        System.out.println("displayNewMessage has been called with string: " + m.getData());
        if(!model.isMuted(m.getSender())) {
            createAddReceiveMessagePane(m.getSender() + ": " + m.getData());
        }
    }

    /**
     * Creates a list of users for the GUI to show
     * @param friendList
     * @throws IOException
     */
    public void createFriendListItemList(List<UserDisplayInfo> friendList) throws IOException {
        for (UserDisplayInfo uInfo : friendList) {
            if (!uInfo.getUsername().equals(model.getUsername())) {
                FriendListItem userItem = new FriendListItem(uInfo);
                if (uInfo.getIsFriend()) {
                    userItem.setFriend();
                }
                friendItemList.add(userItem);
                initFriendListItem(userItem);
            }
        }
    }


    /**
     * Updates the GUI with the new userList
     * @param friendList
     * @throws IOException
     */
    public void showOnlineFriends(List<UserDisplayInfo> friendList) throws IOException {
        friendItemList.clear();
        createFriendListItemList(friendList);
        friendsFlowPane.getChildren().clear();
        for (FriendListItem friendListItem : friendItemList) {
            if (!model.isBlocked(friendListItem)) {
                friendsFlowPane.getChildren().add(friendListItem.getFriendPane());
            }
        }
    }

    /**
     * checking if a friend is blocked
     * @param friendListItem
     * @return
     */


    /**
     * initialize the fxml friendListItem with data
     * @param item
     */
    public void initFriendListItem(FriendListItem item) {
        item.getFriendPane().setOnMouseClicked(Event -> {
            currentChatName = item.getFriendUsername().getText();
            currentChat.setText("Currently chatting with: " + currentChatName);
            try {
                checkFriends();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        MenuItem remove = new MenuItem("Remove");
        MenuItem mute = new MenuItem("Mute");
        MenuItem unmute = new MenuItem("Unmute");
        contextMenu.getItems().add(remove);
        contextMenu.getItems().add(mute);
        contextMenu.getItems().add(unmute);
        mute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.addMuted(item.getFriendUsername().getText());
                item.getFriendPane().setStyle("-fx-background-color: crimson");
            }
        });
        unmute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.removeMuted(item.getFriendUsername().getText());
                item.getFriendPane().setStyle("-fx-background-color: white");
                }
        });

        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.addBlockedFriend(item);
                item.getFriendPane().setVisible(false);
            }
        });
        item.getFriendPane().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(item.getFriendPane(), event.getScreenX(), event.getScreenY());
            }
        });

    }


    /**
     * Opens a GUI window that lets the user choose a file,
     * which is then cached as a FILE object so that it can
     * be sent to the Server or another user later
     */
    public void chooseFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a file to send with your message");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fc.showOpenDialog(chatBox.getScene().getWindow());
        if (selectedFile != null) {
            model.setSelectedFile(selectedFile);
            fileName.setText("Current file: " + model.getSelectedFile().getName());
        }
    }

    /**
     * Sends a specific FILE via the service to the
     * Server (and potentially to another user)
     * @throws IOException
     */
    public void sendFile() throws IOException {
        if (model.getSelectedFile() != null) {
            service.sendMessage(MessageFactory.createFileMessage(model.getSelectedFile(), userName, currentChatName));
            fileName.setText("Current file: none");
        }
    }


    /**
     * Saves messages locally so that they can
     * be loaded the next time you load the client
     */
    public void saveMessages() {
        model.saveReceivedMessages("Client/messages/received.csv");
        model.saveSendMessages("Client/messages/sent.csv");
    }


    /** Loads messages saved during previous sessions */
    public void loadSavedMessages() throws IOException {
        List<String> savedSentMessages = model.loadSavedMessages("Client/messages/sent.csv");
        List<String> savedReceivedMessages = model.loadSavedMessages("Client/messages/received.csv");
        List<String> allSavedMessages = new ArrayList<>();
        combineSavedLists(savedSentMessages,savedReceivedMessages,allSavedMessages);
        for (int i = 0; i < allSavedMessages.size(); i=i+2) {
            if (allSavedMessages.get(i).equals("Me")) {
                createAddSendMessagePane(allSavedMessages.get(i) + ": " + allSavedMessages.get(i + 1));
            }
            else{
                createAddReceiveMessagePane(allSavedMessages.get(i) + ": " + allSavedMessages.get(i + 1));
            }

        }
    }


    /** Combines two saved lists of messages in one list */
    public void combineSavedLists (List<String> savedSentMessages, List<String> savedReceivedMessages,List<String>allSavedMessages){
        int min = Math.min(savedReceivedMessages.size(),savedSentMessages.size());
        int index = 0;
        int tmp = 0;

        for (int i = 0; i < min/2; i++){
            allSavedMessages.add(savedSentMessages.get(tmp));
            allSavedMessages.add(savedSentMessages.get(tmp + 1));
            allSavedMessages.add(savedReceivedMessages.get(tmp));
            allSavedMessages.add(savedReceivedMessages.get(tmp + 1));
            tmp = i + 2;
            index = i;
        }
        index = index * 4;
        if (min == savedSentMessages.size()){
            addElementsAfterIndex(savedReceivedMessages,allSavedMessages,index);
        }
        else if (min == savedReceivedMessages.size()) {
            addElementsAfterIndex(savedSentMessages,allSavedMessages,index);
        }
    }


    /** adds elements of a list to another list and begin from a given index */
    public void addElementsAfterIndex(List<String> savedList,List<String>allSavedMessages,int index){
        for (int i = index; i < savedList.size(); i++){
            allSavedMessages.add(savedList.get(i));
        }
    }
}

