package com.CEYMChatClient;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.IService;
import com.CEYMChatClient.View.FriendListItem;
import com.CEYMChatLib.*;
import com.CEYMChatClient.Services.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Controller for the Client and ClientMain  */
public class ClientController implements IController {

    private ClientModel model;
    private IService service;
    private ArrayList<FriendListItem> friendItemList = new ArrayList<>();
    private String currentChatName;
    private String userName;
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
    private TextArea chatWindow;
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

    private Text fileName;
    private List<UserDisplayInfo> friendList = new ArrayList<>();
    private List<FriendListItem> blockedFriends = new ArrayList<>();

    /** FXML methods**/
    /** Called when the login button is clicked, updates the models state with desired login information */
    @FXML
    public void onClick() throws IOException {
        this.userName = userNameTextField.getText();
        login();
        mainPane.toFront();
    }

    /** Getters and Setters **/
    public IService getService() {
        return service;
    }


    /* Initiates the GUI */
    public void appInit() {
        model = new ClientModel();
        service = new Service(model, this);
        chatWindow.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        chatWindow.setFont(Font.font("Verdana", FontWeight.MEDIUM, 14));
        chatWindow.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        chatWindow.setMouseTransparent(true);
        mainPane.getScene().getWindow().setOnCloseRequest(Event -> {    // Makes sure the client sends a notification to the Server that it has disconnected if the client is terminated
            try {
                saveMessages();
                service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.DISCONNECT, userName), userName));
                service.stop();
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
     */
    public void login() throws IOException {
        appInit();
        service.connectToS();
        service.login(CommandName.SET_USER, userName);
        model.setUsername(userName);
    }


    /**
     * Sends the text in the chatBox to the Server
     * together with whichever user you have chosen
     */
    public void sendString() throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        service.sendMessage(MessageFactory.createStringMessage(toSend, userName, currentChatName));
        chatWindow.appendText("Me: " + toSend + "\n");
        chatWindow.appendText("\n");

    }

    /**
     * Asks the Server for an updated active
     * userlist, called when the Refresh button is pressed
     */
    @FXML
    public void refreshFriendList() {
        try {
            System.out.println("Send refreshFriendList command");
            service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.REFRESH_FRIENDLIST, userName), userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Checks which users have been tagged as friends and notifies the Server if any new friends have been added */
    public void checkFriends() throws IOException {
        System.out.println("Checking friends");
        int changes = 0;
        for (UserDisplayInfo friendInfo : friendList) {         // Removes friends that have been deselected
            if (!friendInfo.getIsFriend()) {
                if (friendList.size() > 0 && friendList.contains(friendInfo)) {
                    friendList.remove(friendInfo);
                    changes++;
                }
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
            System.out.println("Sending list to server");
            service.sendMessage(MessageFactory.createFriendInfoList(friendList, userName, userName));
            return;
        }
    }


    /**
     * Currently unused, sends a command to the Server
     * to notify it that the user wants to initiate a
     * chat with someone, currently a message contains
     * a String with the username of the intended receiver instead
     */
    public void requestChat() {
        try {
            service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.REQUEST_CHAT, "user2"), userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Updates the GUI with text from a new message*/
    public void displayNewMessage(Message m) {
        System.out.println("displayNewMessage has been called with string: " + m.getData());
        chatWindow.appendText(m.getSender() + ": " + m.getData() + "\n");
        chatWindow.appendText("\n");

    }

    /** Creates a list of users for the GUI to show */
    public void createFriendListItemList(ArrayList<UserDisplayInfo> friendList) throws IOException {
        System.out.println("New list of friendItems created");
        for (UserDisplayInfo uInfo : friendList) {
            System.out.println("User added: " + uInfo.getUsername());
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


    /** Updates the GUI with the new userList */
    public void showOnlineFriends(ArrayList<UserDisplayInfo> friendList) throws IOException {
        friendItemList.clear();
        System.out.println("FriendListItems are being created");
        createFriendListItemList(friendList);
        friendsFlowPane.getChildren().clear();
        for (FriendListItem friendListItem : friendItemList) {
            if (!isBlocked(friendListItem)) {
                friendsFlowPane.getChildren().add(friendListItem.getFriendPane());
            }
        }
    }

    /** checking if a friend is blocked */
    public boolean isBlocked(FriendListItem friendListItem) {
        for (FriendListItem b : blockedFriends) {
            if (b.getFriendUsername().getText().equals(friendListItem.getFriendUsername().getText())) {
                return true;
            }
        }
        return false;
    }


    /** initialize the fxml friendListItem with data */
    public void initFriendListItem(FriendListItem item) {
        item.getFriendPane().setOnMouseClicked(Event -> {
            currentChatName = item.getFriendUsername().getText();
            currentChat.setText("Currently chatting with: " + currentChatName);
            System.out.println("CurrentChat set to: " + currentChatName);
            try {
                checkFriends();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        MenuItem block = new MenuItem("Block");
        contextMenu.getItems().add(block);
        block.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                blockedFriends.add(item);
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
     * Opene a GUI window that lets the user choose a file,
     * which is then cached as a File object so that it can
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
     * Sends a specific File via the service to the
     * Server (and potentially to another user)
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
        System.out.println("Messages saved.");
        model.saveReceivedMessages("Client/messages/received.csv");
        model.saveSendMessages("Client/messages/sent.csv");
    }


    /** Loads messages saved during previous sessions */
    public void loadSavedMessages() throws IOException {
        ArrayList<String> savedSentMessages = model.loadSavedMessages("Client/messages/sent.csv");
        ArrayList<String> savedReceivedMessages = model.loadSavedMessages("Client/messages/received.csv");
        ArrayList<String> allSavedMessages = new ArrayList<>();
        combineSavedLists(savedSentMessages,savedReceivedMessages,allSavedMessages);
        for (int i = 0; i < allSavedMessages.size(); i=i+2) {
            chatWindow.appendText(allSavedMessages.get(i) + ": " + allSavedMessages.get(i + 1) + "\n");
            chatWindow.appendText("\n");
        }
    }


    /** Combines two saved lists of messages in one list */
    public void combineSavedLists (ArrayList<String> savedSentMessages, ArrayList<String> savedReceivedMessages,ArrayList<String>allSavedMessages){
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
    public void addElementsAfterIndex(ArrayList<String> savedList,ArrayList<String>allSavedMessages,int index){
        for (int i = index; i < savedList.size(); i++){
            allSavedMessages.add(savedList.get(i));
        }
    }
}

