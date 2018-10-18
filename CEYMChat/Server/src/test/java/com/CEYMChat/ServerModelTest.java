package com.CEYMChat;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.Assert.*;

public class ServerModelTest {
    ServerModel testModel;

    @Test
    public void performCommand() throws IOException {
        testModel  = new ServerModel();
        testModel.addUser(new User());
        testModel.performCommand(new Command(CommandName.SET_USER, "true"), testModel.getUserList().get(0).getUsername());
        assertEquals(testModel.getUserList().get(0).getUsername(), "true");
        testModel.getServerSocket().close();
    }

    @Test
    public void addUser() throws IOException {
        testModel = new ServerModel();
        User testUser = new User();
        testModel.addUser(testUser);
        assertEquals(testModel.getUserList().get(0), testUser);
        testModel.getServerSocket().close();
    }

    @Test
    public void sendMessage() throws IOException {
        testModel  = new ServerModel();
       User testUser = new User();
        SocketHandler testHandler = new SocketHandler(testModel);
        testHandler.start();
        Socket socket = new Socket("localhost", 9000);
        testUser.initThreads(socket, testModel);
        testUser.setUsername("testUser");
        testModel.addUser(testUser);
        Message testMessage = MessageFactory.createStringMessage("Hello world!", "testUser", "testUser");
        testModel.sendMessage(testMessage,"testUser");
        assertEquals(testUser.getWriter().getOutMessage(),testMessage);
        testModel.getServerSocket().close();
    }

    @Test
    public void getUserByUsername() throws IOException {
        testModel = new ServerModel();
        User testUser = new User();
        testUser.setUsername("testUser");
        testModel.addUser(testUser);
        assertEquals(testUser, testModel.getUserByUsername(testUser.getUsername()));
        testModel.getServerSocket().close();

    }

}