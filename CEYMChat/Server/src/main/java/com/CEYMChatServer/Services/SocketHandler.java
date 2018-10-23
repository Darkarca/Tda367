package com.CEYMChatServer.Services;

import com.CEYMChatServer.Model.User;
import com.CEYMChatServer.Model.ServerModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** SocketHandler is responsible for
 * * continuously allowing new users to connect to the Server
 */
public class SocketHandler{
    private ServerSocket serverSocket;
    private ServerModel model;
    private List<IReader> readers;
    boolean running = true;
    public SocketHandler(ServerModel model){
        this.model = model;
        this.serverSocket = model.getServerSocket();
        readers = new ArrayList<>();
    }

    public void start() {
        new Thread(() -> {
            while (running) {
                try {
                    System.out.println("Looking for socket");
                    this.connectSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);             // Sleeps for a short amount of time before connecting a new user
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void connectSocket() throws IOException {
        Socket acceptedSocket = serverSocket.accept();
        User newUser = new User();
        newUser.setOnline(true);
        model.addUser(newUser);
        newUser.initIO(acceptedSocket);
        IReader reader = new Reader(model, acceptedSocket);          // Constructor for reader starts the Thread
        readers.add(reader);
    }

    public List<IReader> getReaders() {
        return readers;

    }

    public void stop() {
        running = false;
    }
}
