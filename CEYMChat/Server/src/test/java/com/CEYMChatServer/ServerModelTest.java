package com.CEYMChatServer;

import com.CEYMChatLib.Command;
import com.CEYMChatLib.CommandName;
import com.CEYMChatLib.Message;
import com.CEYMChatLib.MessageFactory;
import com.CEYMChatServer.Model.ServerModel;
import com.CEYMChatServer.Model.User;
import com.CEYMChatServer.Services.SocketHandler;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;

public class ServerModelTest {
    private ServerModel testModel;

    @Test
    public void performCommand() throws IOException, InterruptedException {
        testModel  = new ServerModel();
        SocketHandler testHandler = new SocketHandler(testModel);
        Socket socket = new Socket("localhost", 9000);
        Thread.sleep(2000);
        testHandler.start();
        Thread.sleep(2000);
        testModel.performCommand(new Command(CommandName.SET_USER, "true"), testModel.getUserList().get(0).getUsername());
        assertEquals(testModel.getUserList().get(0).getUsername(), "true");
        testModel.performCommand(new Command(CommandName.REFRESH_FRIENDLIST, testModel.getUserList().get(0).getUsername()),testModel.getUserList().get(0).getUsername());
        assertEquals("ARRAYLIST",testModel.getUserList().get(0).getWriter().getOutMessage().getType().getSimpleName());
        testModel.performCommand(new Command(CommandName.DISCONNECT, testModel.getUserList().get(0).getUsername()),testModel.getUserList().get(0).getUsername());
        assertEquals(0,testModel.getUserList().size());
        socket.close();
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
        //socket.close();
        testModel.getServerSocket().close();
    }

    @Test
    public void findUserByUsername() throws IOException {
        testModel = new ServerModel();
        User testUser = new User();
        testUser.setUsername("testUser");
        testModel.addUser(testUser);
        assertEquals(testUser, testModel.getUserByUsername(testUser.getUsername()));
        testModel.getServerSocket().close();

    }

    @Test
    public void updateUserLists() throws IOException, InterruptedException {
        testModel = new ServerModel();
        SocketHandler testHandler = new SocketHandler(testModel);
        Socket socket = new Socket("localhost", 9000);
        Thread.sleep(2000);
        testHandler.start();
        Thread.sleep(2000);
        testModel.getUserList().get(0).setUsername("testUser");
        testModel.updateUserLists();
        assertEquals("ARRAYLIST", testModel.getUserList().get(0).getWriter().getOutMessage().getType().getSimpleName());
        //socket.close();
        testModel.getServerSocket().close();
    }

    @Test
    public void sendFile() throws IOException, InterruptedException {
        testModel = new ServerModel();
        SocketHandler testHandler = new SocketHandler(testModel);
        Socket socket = new Socket("localhost", 9000);
        Thread.sleep(2000);
        testHandler.start();
        Thread.sleep(2000);
        testModel.getUserList().get(0).setUsername("testUser");
        File toSend = new File("pom.xml");
        testModel.sendFile(toSend.getName(),MessageFactory.createFileMessage(toSend,testModel.getUserList().get(0).getUsername(),testModel.getUserList().get(0).getUsername()));
        assertEquals(toSend,testModel.getUserList().get(0).getWriter().getOutMessage().getData());
        testModel.getServerSocket().close();
    }
}