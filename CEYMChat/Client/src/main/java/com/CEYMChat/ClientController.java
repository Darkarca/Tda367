package com.CEYMChat;

import com.CEYMChat.Model.ClientModel;
import com.CEYMChat.Services.IService;
import com.CEYMChat.Services.Service;
import com.CEYMChat.View.FriendListItem;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
 * Controller for the Client.
 */
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
    private TextArea sendWindow;
    @FXML
    private TextArea receiveWindow;
    @FXML
    private TextField sendToTextField;
    @FXML
    private FlowPane friendsFlowPane;
    @FXML
    private TextField loginTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button fileSend;
    @FXML
    private Text fileName;
    private List<UserDisplayInfo> friendList = new ArrayList<>();



    /** FXML methods**/


    /**
     * Captures input from user and send makes use of model to send message
     */
    @FXML
    public void onClick() throws IOException {
        this.userName = loginTextField.getText();
        login();
        mainPane.toFront();
        toggleChatBox();
    }




    /********************************/


    /**Getters and Setters **/

    public IService getService() {
        return service;
    }

    /********************************/


    public void appInit() {
        model = new ClientModel();
        service = new Service(model, this);
        receiveWindow.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        sendWindow.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        receiveWindow.setBorder(null);
        receiveWindow.setMouseTransparent(true);
        sendWindow.setMouseTransparent(true);



        mainPane.getScene().getWindow().setOnCloseRequest(Event -> {
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
        if(received.exists() && sent.exists()) {
            try {
                loadSavedMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void login() throws IOException {

        appInit();
        service.connectToS();
        service.login(CommandName.SET_USER, userName);
        model.setUsername(userName);



    }

    public void sendString()throws IOException {
        String toSend = chatBox.getText();
        chatBox.setText("");
        service.sendMessage(MessageFactory.createStringMessage(toSend, userName, currentChatName));
        sendWindow.appendText("Me: "+toSend+"\n");
        receiveWindow.appendText("\n");

    }

    @FXML
    public void refreshFriendList() {
        try {
            System.out.println("Send refreshFriendList command");
            service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.REFRESH_FRIENDLIST, userName), userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toggleChatBox () {
        if (chatBox.isEditable())
            chatBox.setEditable(false);
        else {
            chatBox.setEditable(true);
        }
    }

        public void checkFriends() throws IOException {
        System.out.println("Checking friends");
        int changes = 0;
            for (UserDisplayInfo friendInfo : friendList) {
                if (!friendInfo.getIsFriend()) {
                    if(friendList.size()>0 && friendList.contains(friendInfo)) {
                        friendList.remove(friendInfo);
                        changes++;
                    }
                }
            }
            for (FriendListItem fL : friendItemList) {
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
            if (changes != 0) {
                System.out.println("Sending list to server");
                service.sendMessage(MessageFactory.createFriendInfoList(friendList, userName, userName));
                return;
            }
        }

    public void requestChat(){
                            try {
                                service.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.REQUEST_CHAT, "user2"), userName));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

    public void displayNewMessage (String s){

        System.out.println("displayNewMessage has been called with string: " + s);
        receiveWindow.appendText(s+"\n");
        sendWindow.appendText("\n");

    }

    public void createFriendListItemList (ArrayList < UserDisplayInfo > friendList) throws IOException {
            System.out.println("New list of friendItems created");
            for (UserDisplayInfo uInfo : friendList) {
                System.out.println("User added: " + uInfo.getUsername());
                if (!uInfo.getUsername().equals(model.getUsername())) {
                    FriendListItem userItem = new FriendListItem(uInfo.getUsername());
                    userItem.setUInfo(uInfo);
                    if(uInfo.getIsFriend()){
                        userItem.setFriend();
                    }
                    friendItemList.add(userItem);
                    userItem.getFriendPane().setOnMouseClicked(Event -> {
                        currentChatName = userItem.getFriendUsername().getText();
                        currentChat.setText("Currently chatting with: " + currentChatName);
                        System.out.println("CurrentChat set to: " + currentChatName);
                        try {
                            checkFriends();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }

    public void showOnlineFriends (ArrayList < UserDisplayInfo > friendList) throws IOException {
            friendItemList.clear();
            System.out.println("FriendListItems are being created");
            createFriendListItemList(friendList);
            friendsFlowPane.getChildren().clear();
            for (FriendListItem friendListItem : friendItemList) {
                friendsFlowPane.getChildren().add(friendListItem.getFriendPane());
            }
        }

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

    public void sendFile() throws IOException {
        if (model.getSelectedFile() != null) {
            service.sendMessage(MessageFactory.createFileMessage(model.getSelectedFile(), userName, currentChatName));
            fileName.setText("Current file: none");
        }
    }

    public void saveMessages () {
            System.out.println("Messages saved.");
            model.saveReceivedMessages("Client/messages/received.csv");
            model.saveSendMessages("Client/messages/sent.csv");
        }

    public void loadSavedMessages () throws IOException {
            ArrayList<String> savedSentMessages = model.loadSavedMessages("Client/messages/sent.csv");
            ArrayList<String> savedReceivedMessages = model.loadSavedMessages("Client/messages/received.csv");
            for (int i = 0; i < savedSentMessages.size(); i = i + 2) {
                sendWindow.appendText(savedSentMessages.get(i) +": " + savedSentMessages.get(i+1)+ "\n");
                receiveWindow.appendText(savedReceivedMessages.get(i) +": " + savedReceivedMessages.get(i+1)+ "\n");
            }
        }
}

