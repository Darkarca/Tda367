package com.CEYMChat;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Writer implements Runnable {
    private ServerModel model;
    private Socket socket;



    private ObjectOutputStream outputStream;
    private Message outMessage;
    private Message lastMsg;
    private List userInfoList = new ArrayList();




    public Writer(ServerModel model, Socket socket) {
        this.model = model;
        this.socket = socket;
        {
            try {
                outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }

    }


    @Override
    public void run() {

        while(true){
            try {
                outputStream.writeObject(outMessage);
                if(outMessage != lastMsg) {
                    lastMsg = outMessage;
                    System.out.println("Object written to stream: " + outMessage.toString());
                }

                if (model.inlogged == true) {
                    outputStream.writeObject(createUserInfoList(model.getUserList()));
                    model.inlogged = false;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOutMessage(Message m){
        outMessage = m;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public Message createUserInfoList (List<User> users){
        userInfoList.clear();
        for (User u : users){
            UserDisplayInfo uInfo = new UserDisplayInfo();
            userInfoInit(u,uInfo);
            userInfoList.add(uInfo);
        }
        Message listMessage = MessageFactory.createFriendInfoList(userInfoList);
        return listMessage;
    }

    public void userInfoInit(User u, UserDisplayInfo uInfo){
        uInfo.setUsername(u.getUsername());
        //uInfo.setStatus();
        //uInfo.setImg();
    }
}


