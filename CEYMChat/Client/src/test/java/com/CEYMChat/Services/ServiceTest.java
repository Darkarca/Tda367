package com.CEYMChat.Services;

import com.CEYMChat.ClientController;
import com.CEYMChat.IController;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceTest {

    ServerSocket serverSocket;
    OutputStream outputStream;
    InputStream inputStream;
    Socket socket;
    Service service;
    IController clientController;
    boolean serverOn = false;
    int sent = 2;

    public void setUpServer() throws IOException {
        {
            serverSocket = new ServerSocket(9000);
            serverOn = true;
            new Thread(()->{
                try {
                    while (serverOn){
                        socket = serverSocket.accept();
                        System.out.println("Connected socket");
                        outputStream = socket.getOutputStream();
                        outputStream.write(sent);
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
    public void connectToS() throws IOException, ClassNotFoundException {
        /*
        inIt();

        // Test send-receive


        inputStream = service.getMessageInStream();
        int received = inputStream.read();
        Assert.assertEquals(sent, received);
        serverOn = false;
        */
    }


    @Test
    public void read() {
    }


    @Test
    public void setMessageOut() {
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