package com.CEYMChatServer;

import com.CEYMChatServer.Model.User;
import com.CEYMChatServer.Model.ServerModel;
import com.CEYMChatServer.Services.IReader;
import com.CEYMChatServer.Services.Reader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** SocketHandler is responsible for
 * * continuously allowing new users to connect to the Server
 */
class SocketHandler{
    private ServerSocket serverSocket;
    private ServerModel model;
    private List<IReader> readers;
    private int port = 9000;
    boolean running = true;
    SocketHandler(ServerModel model, int port){
        this.model = model;
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not initialize the serverSocket, restarting...");
            //exitcode 5 tells the start script to restart
            System.exit(5);
            e.printStackTrace();
        }
        readers = new ArrayList<>();
    }
    SocketHandler(ServerModel model){
        this.model = model;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {

            System.out.println("Could not initialize the serverSocket, restarting...");
            //exitcode 5 tells the start script to restart
            System.exit(5);
            e.printStackTrace();
        }
        readers = new ArrayList<>();
    }

    void start() {
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

    private void connectSocket() throws IOException {
        Socket acceptedSocket = serverSocket.accept();
        User newUser = new User();
        newUser.setOnline(true);
        model.addUser(newUser);
        newUser.initIO(acceptedSocket);
        IReader reader = new Reader(acceptedSocket);          // Constructor for reader starts the Thread
        readers.add(reader);
        reader.register(model);
    }

    List<IReader> getReaders() {
        return readers;
    }

    void stop() {
        running = false;
    }

    void closeSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            onCloseSocketException();
            e.printStackTrace();
        }
    }

    /**
     * If closeSocket throws exception, we attempt again
     */
    void onCloseSocketException(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Was interrupted from sleep");
            e.printStackTrace();
        }
        closeSocket();

    }
}