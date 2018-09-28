package com.CEYMChat;

import com.CEYMChat.Command;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerModel {

    List<Socket> clientList = new ArrayList<Socket>();
    ObjectInputStream messageInStream;
    ObjectOutputStream messageOutStream;

    public void logInUser(Command c) {
        if (checkUser(c.getCommandData())) {
            try {
                int newUser = clientList.size();
                clientList.add(new Socket());
                Socket thisLogIn;
                thisLogIn = clientList.get(newUser);
                thisLogIn = ServerMain.serverSocket.accept();
                messageInStream = new ObjectInputStream(thisLogIn.getInputStream());
                messageOutStream = new ObjectOutputStream(thisLogIn.getOutputStream());

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void registerUser(){

    }

    public void performCommand(Command c){
    }

    public void startSession(){

    }

    public boolean checkUser(){
            return false;
    }

}
