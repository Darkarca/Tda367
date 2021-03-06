package com.CEYMChatServer.Model;

import com.CEYMChatLib.*;
import com.CEYMChatServer.IMessageObserver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** Server model class */
public class ServerModel implements IMessageObserver {

    private List<User> userList = new ArrayList<>();


    /**
     * Getters and setters
     */
    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * Performs a received command,
     *
     * @param command COMMAND to be executed
     * @param sender  User that sent the command
     */
    private void performCommand(Command command, UserInfo sender) {
        switch (command.getCommandName()) {
            case SET_USER: {
                setUser(sender);
            }
            break;
            case REFRESH_FRIENDLIST: {
                refreshFriendList(command, sender);
            }
            break;
            case DISCONNECT: {
                disconnect(sender);
            }
            break;
            case ADD_FRIEND:
                addFriend(getUserByUsername(sender.getUsername()), getUserByUsername(command.getCommandData()));
                break;
        }
    }

    /**
     * Adds a User(toBeAdded) to the User's(adder) friendlist
     *
     * @param adder
     * @param toBeAdded
     */
    private void addFriend(User adder, User toBeAdded) {
        adder.addFriend(toBeAdded);
    }

    /**
     * Sets the username of a user so that it can be
     * identified uniformly between the client and server
     */
    public void setUser(UserInfo sender) {
        userList.get(userList.size() - 1).setuInfo(sender);
        System.out.println("COMMAND performed: 'setUser'" + userList.get(userList.size()-1).getUInfo().getUsername());
        updateUserLists();
    }

    public void friendSync(Message message) {
        getUserByUsername(message.getSender().getUsername()).syncFriends(message);
    }

    /**
     * Sends an updateNewMessage active userlist to all active clients,
     * also merges the list with each users individual friendslist
     */
    public void refreshFriendList(Command command, UserInfo sender) {
        User user = getUserByUsername(sender.getUsername());
        user.syncFriends(getUserInfoMessage());
        user.sendMessage(user.checkFriends(getUserInfoMessage()));
        System.out.println("COMMAND performed: 'refreshFriendList '" + command.getCommandData());
    }


    /**
     * Disconnects the user by removing it from the Servers
     * userlist so that the server won't point to a null outputStream
     */
    public void disconnect(UserInfo sender) {
        User user = getUserByUsername(sender.getUsername());
        user.setOnline(false);
        userList.remove(user);
        updateUserLists();

    }


    /**
     * Sends user information via UserInfo objects to the recipient.
     */
    public Message getUserInfoMessage() {
        List<UserInfo> list = new ArrayList<UserInfo>();
        for (User user : userList) {
            UserInfo uInfo = user.getUInfo();
            uInfo.setInetAddress(user.getSocket().getInetAddress());
            if (user.isOnline()) {
                uInfo.setOnlineIndicator(true);
            }
            list.add(uInfo);
        }
        return MessageFactory.createUsersDisplayInfoMessages(list, null, null);
    }


    /**
     * Updates user lists
     */
    public void updateUserLists() {
        for (User u : userList) {
            u.sendMessage(u.checkFriends(getUserInfoMessage()));
        }
        System.out.println("Userlists updated!");
    }

    /**
     * Displays a message on the server console.
     *
     * @param message Message to be displayed.
     */
    private void displayMessage(Message message) {
        System.out.println(message.getSender() + ": " + message.getData());
    }

    /**
     * Sends a message to the correct receiver.
     *
     * @param message  Message to be sent.
     * @param receiver Name of receiver.
     */
    public void sendMessage(Message message, String receiver) {
        try {
            User user = getUserByUsername(receiver);
            user.sendMessage(message);
        }catch(NullPointerException e){
            System.out.println("No user found, message not forwaded");
        }
    }

    /**
     * Retrieves a User by searching for a username STRING.
     *
     * @param username Username to search for
     */
    public User getUserByUsername(String username) {
        for (User u : userList) {
            if (u.getUInfo().getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Sends a file to a clients device
     *
     * @param s       Name of file.
     * @param message Message to send alongside the FILE
     *                containing things such as filesize, sender and receiver
     */
    public void sendFile(String s, Message message) throws IOException {
        sendMessage(message, message.getReceiver());
        File toSend = new File(s);
        byte[] sentFile = new byte[(int) toSend.length()];
        FileInputStream fileInput = new FileInputStream(toSend);
        BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
        bufferedInput.read(sentFile, 0, sentFile.length);
        OutputStream outputStream = getUserByUsername(message.getReceiver()).getWriter().getSocket().getOutputStream();

        outputStream.write(sentFile, 0, sentFile.length);
        outputStream.flush();

    }


    @Override
    public void updateNewMessage(Message message) {
        MessageType msgType = MessageType.valueOf(message.getType().getSimpleName().toUpperCase());
        switch (msgType) {
            case COMMAND: {                                     // A message containing a command is sent to the model so that is can be performed, we stop the thread if the command tells us to disconnect
                performCommand((Command)(message.getData()),message.getSender());
                break;
            }
            case STRING: {                                      // A string message is simply sent to the model and redistributed to the correct client
                displayMessage(message);
                sendMessage(message,message.getReceiver());
                break;
            }
            case ARRAYLIST: {
                friendSync(message);
                performCommand(new Command(CommandName.REFRESH_FRIENDLIST,message.getSender().getUsername()),message.getSender());
                break;
            }
            case MESSAGEFILE: {
                try {
                    sendFile("Server/messages/" + ((MessageFile)message.getData()).getFileName(), message);
                } catch (IOException e) {
                    System.out.println("File was not found, try again");
                    e.printStackTrace();
                }
                break;
            }
        }
    }



}
