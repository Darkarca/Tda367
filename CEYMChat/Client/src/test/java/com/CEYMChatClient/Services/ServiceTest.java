package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.ClientController;
import com.CEYMChatClient.Controller.IController;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceTest {

    private ServerSocket serverSocket;
    private OutputStream outputStream;
   // private InputStream inputStream;
    private Socket socket;
    private Service service;
    private IController clientController;
    private boolean serverOn = false;

    public void setUpServer() throws IOException {
        {
            serverSocket = new ServerSocket(9000);
            serverOn = true;
            new Thread(()->{
                try {
                    while (serverOn){
                        socket = serverSocket.accept();
                        outputStream = socket.getOutputStream();
                        outputStream.write(2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void inIt() throws IOException {
        setUpServer();
        clientController = new ClientController();
        clientController.login();
        service = (Service) ((ClientController) clientController).getService();
    }

    @Test
    public void connectToS() {
    }

    @Test
    public void read() {
    }

    @Test
    public void stop() {
    }

    @Test
    public void sendMessage() {
    }


    @Test
    public void displayNewMessage() {
    }


    @Test
    public void processMessage() {
    }


    @Test
    public void displayFriendList() {
    }
}