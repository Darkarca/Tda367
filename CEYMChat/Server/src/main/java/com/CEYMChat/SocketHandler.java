package com.CEYMChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler{
    private ServerSocket serverSocket;
    private ServerModel model;
    public SocketHandler(ServerModel model){
        this.model = model;
        this.serverSocket = model.getServerSocket();
    }


    public synchronized void start() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Looking for socket");
                    this.connectSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void connectSocket() throws IOException {
        Socket s = serverSocket.accept();
        User newUser = new User();
        newUser.startThreads(s, model);
        model.addUser(newUser);
    }
}
