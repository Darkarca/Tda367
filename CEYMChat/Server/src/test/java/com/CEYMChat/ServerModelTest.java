package com.CEYMChat;

import com.CEYMChat.Model.ServerModel;
import com.CEYMChat.Model.User;
import com.CEYMChat.Services.SocketHandler;
import org.junit.Test;

import java.io.IOException;
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
    public void sendMessage() throws IOException, InterruptedException {
        testModel  = new ServerModel();
        SocketHandler testHandler = new SocketHandler(testModel);
        Socket socket = new Socket("localhost", 9000);
        Thread.sleep(2000);
        testHandler.start();
        Thread.sleep(2000);
        testModel.getUserList().get(0).setUsername("testUser");
        Message testMessage = MessageFactory.createStringMessage("Hello world!", "testUser", "testUser");
        testModel.sendMessage(testMessage,"testUser");
        assertEquals(testModel.getUserByUsername("testUser").getWriter().getOutMessage(),testMessage);
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