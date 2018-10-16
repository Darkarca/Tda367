package com.CEYMChat.Model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.*;

public class ClientModelTest {

    @Before
    public void setUp() throws IOException {
            ServerSocket serverSocket = new ServerSocket(9000);

        new Thread(()->{
            while (true){
                try {
                    serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Test
    public void connectToServer() {
    }

    @Test
    public void login() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void getLoginStatus() {
    }

    @Test
    public void getModelInstance() {
    }

    @Test
    public void setFriendList() {
    }

    @Test
    public void createFriendList() {
    }

    @Test
    public void getfriendList() {
    }

    @Test
    public void setUsername() {
    }

    @Test
    public void getUsername() {
    }

    @Test
    public void getConnectionService() {
    }
}