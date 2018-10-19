package com.CEYMChat.Services;

import com.CEYMChat.Model.User;
import com.CEYMChat.Model.ServerModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
                /** SocketHandler is responsible for continuously allowing new users to connect to the Server**/
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
                    Thread.sleep(1000);             // Sleeps for a short ammount of time before connecting a new user
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
        IReader reader = new Reader(model, s);          // Constructor for reader starts the Thread
    }
}
