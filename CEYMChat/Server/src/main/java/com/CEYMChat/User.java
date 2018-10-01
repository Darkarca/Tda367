package com.CEYMChat;

import java.net.Socket;

public class User {

    Socket socket;
    String username;


    public User(Socket s, String username){
        this.socket = s;
        this.username = username;


    }
}
