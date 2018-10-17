package com.CEYMChat;

import org.junit.Test;

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
    public void displayMessage() {
    }

    @Test
    public void sendMessage() {
       /* User testUser = new User();
        testUser.startThreads(new Socket(), testModel);
        Message testMessage = MessageFactory.createStringMessage("Hello world!", "testUser", "testUser");
        testModel.sendMessage(testMessage,"testUser");
        assertEquals(testUser.getWriter().getOutMessage(),testMessage);
        */
    }

    @Test
    public void getUserByUsername() {
        User testUser = new User();
        testUser.setUsername("testUser");
        testModel.addUser(testUser);
        assertEquals(testUser, testModel.getUserByUsername(testUser.getUsername()));

    }

}