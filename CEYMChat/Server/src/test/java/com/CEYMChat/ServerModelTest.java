package com.CEYMChat;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;

public class ServerModelTest {
    ServerModel testModel = new ServerModel();

    @Test
    public void performCommand() {
        testModel.addUser(new User());
        testModel.performCommand(new Command(CommandName.SET_USER, "true"), testModel.getUserList().get(0).getUsername());
        assertEquals(testModel.getUserList().get(0).getUsername(), "true");
    }

    @Test
    public void addUser() {
        User testUser = new User();
        testModel.addUser(testUser);
        assertEquals(testModel.getUserList().get(0), testUser);
    }

    @Test
    public void sendMessage() throws IOException {
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
    }

    @Test
    public void getUserByUsername() {
        User testUser = new User();
        testUser.setUsername("testUser");
        testModel.addUser(testUser);
        assertEquals(testUser, testModel.getUserByUsername(testUser.getUsername()));

    }

}