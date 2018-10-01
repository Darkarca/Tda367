package com.CEYMChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler extends Thread{
    ServerSocket serverSocket;
    ServerModel model;
    public SocketHandler(ServerModel model){
        this.model = model;
        this.serverSocket = model.getServerSocket();

    }

    @Override
    public synchronized void start() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Looking for socket");
                    connectSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    public synchronized void connectSocket() throws IOException {
        model.addSocket( serverSocket.accept());
    }
}
