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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        System.out.println(s.isConnected());
        User newUser = new User();
        model.addUser(newUser);
        newUser.initIO(s, model);

    }
}
