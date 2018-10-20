package com.CEYMChat.Model;


import com.CEYMChat.Command;
import com.CEYMChat.Message;
import com.CEYMChat.MessageFactory;
import com.CEYMChat.UserDisplayInfo;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class  ServerModel {
    private ServerSocket serverSocket;
    private List<User> userList = new ArrayList<>();
    private int port = 9000;
    {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *Performs a received command,
     * @param c Command to be executed
     * @param sender User that sent the command
     */
    public void performCommand(Command c, String sender) {
        switch(c.getCommandName()){
            case SET_USER: {                // Sets the username of a user so that it can be identified uniformly between the client and server
                userList.get(userList.size()-1).setUsername(c.getCommandData());
                System.out.println("Command performed: 'setUser'" + c.getCommandData());
                updateUserLists();
                break;
            }
            case REFRESH_FRIENDLIST:        // Sends an update active userlist to all active clients, also merges the list with each users individual friendslist
                User u = getUserByUsername(sender);
                u.syncFriends(sendUserInfo());
                u.sendMessage(u.checkFriends(sendUserInfo()));
                System.out.println("Command performed: 'refreshFriendList '" + c.getCommandData());
                break;
            case DISCONNECT:                // Disconnects the user by removing it from the Servers userlist so that the server won't point to a null outputStream
                User user = getUserByUsername(sender);
                userList.remove(user);
                updateUserLists();
                break;
            case REGISTER:                  // Not yet supported, supposed to create a new user in a Serverlocal file containing information about all users
                break;
            case ADD_FRIEND:                // Not yet supported, supposed to add a specific user to a specific users individual friendlist in a Serverlocal file containing information about all users
                break;
            case REQUEST_CHAT:              // Not yet supported, supposed to initiate a chat between two (or more) users
        }
    }

    /**
     * Sends user information via UserDisplayInfo objects to the recipient.
     */
    public Message sendUserInfo(){
        List<UserDisplayInfo> list = new ArrayList<UserDisplayInfo>();
        for (User u:userList) {
            UserDisplayInfo u1 = new UserDisplayInfo();
            u1.setUsername(u.getUsername());
            u1.setInetAddress(u.getSocket().getInetAddress());
            list.add(u1);
        }
        return MessageFactory.createFriendInfoList(list, null, null);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void addUser(User user) {
        userList.add(user);
    }

    public void updateUserLists(){
        for (User u:userList) {
            //u.syncFriends(sendUserInfo());
            u.sendMessage(u.checkFriends(sendUserInfo()));
        }
        System.out.println("Userlists updated!");
    }

    /**
     * Displays a message on the server console.
     * @param m Message to be displayed.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void displayMessage(Message m) throws IOException, ClassNotFoundException {
        System.out.println(m.getSender() + ": " + m.getData());
    }

    /**
     * Sends a message to the correct receiver.
     * @param m Message to be sent.
     * @param receiver Name of receiver.
     */
    public void sendMessage(Message m, String receiver){
        User u = getUserByUsername(receiver);
        u.sendMessage(m);
    }

    /**
     * Retrieves a User by searching for a username String.
     * @param username Username to search for
     * @return
     */
    public User getUserByUsername(String username){
        for (User u : userList){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    /**
     * Sends a file to a clients device
     * @param s Name of file.
     * @param m Message to send alongside the File containing things such as filesize, sender and receiver
     * @throws IOException
     */
    public void sendFile(String s, Message m) throws IOException {
        sendMessage(m,m.getReceiver());
        File toSend = new File(s);
        byte[] sentFile = new byte[(int)toSend.length()];
        FileInputStream fileInput = new FileInputStream(toSend);
        BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
        bufferedInput.read(sentFile,0,sentFile.length);
        OutputStream outputStream = getUserByUsername(m.getReceiver()).getWriter().getSocket().getOutputStream();
        outputStream.write(sentFile,0,sentFile.length);
    }
}

