package com.CEYMChat;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServerModelTest {

    @Test
    public void performCommand() {
        ServerModel testModel = new ServerModel();
        testModel.addUser(new User());
        testModel.performCommand(new Command("setUser", "true"), testModel.getUserList().get(0).getUsername());
        assertEquals(testModel.getUserList().get(0).getUsername(), "true");
    }

    @Test
    public void addUser() {
    }

    @Test
    public void displayMessage() {
    }

    @Test
    public void sendMessage() {
    }

    @Test
    public void getUserByUsername() {
    }

    @Test
    public void isLoggedIn() {
    }

    @Test
    public void setLoggedIn() {
    }
}