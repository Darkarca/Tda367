package com.CEYMChat;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain{

    static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8989);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}