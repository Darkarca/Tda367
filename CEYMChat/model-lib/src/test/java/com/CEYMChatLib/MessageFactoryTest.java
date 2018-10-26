package com.CEYMChatLib;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MessageFactoryTest {

    @Test
    public void createStringMessage() {
        UserInfo uInfo = new UserInfo();
        Message expected =  new Message("test1",uInfo,"test3");
        Message actual = MessageFactory.createStringMessage("test1",uInfo,"test3");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getReceiver(),actual.getReceiver());
        assertEquals(expected.getType(),actual.getType());
    }

    @Test
    public void createFileMessage() {
        UserInfo uInfo = new UserInfo();
        MessageFile file = new MessageFile(new File(""));
        Message expected =  new Message(file,uInfo,"test3");
        Message actual = MessageFactory.createFileMessage(file,uInfo,"test3");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getReceiver(),actual.getReceiver());
        assertEquals(expected.getType(),actual.getType());
    }

    @Test
    public void createCommandMessage() {
        UserInfo uInfo = new UserInfo();

        Command command = new Command(CommandName.SET_USER,"test");
        Message expected =  new Message<>(command,uInfo,"test3");
        Message actual = MessageFactory.createCommandMessage(command,uInfo);
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getType(),actual.getType());
    }

    @Test
    public void createFriendInfoList() {
        UserInfo uInfo = new UserInfo();
        List<UserInfo> friendInfoList = new ArrayList<>();
        friendInfoList.add(new UserInfo());
        Message expected =  new Message<>(friendInfoList,uInfo,"test3");
        Message actual = MessageFactory.createUsersDisplayInfoMessages(friendInfoList,uInfo,"test3");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getReceiver(),actual.getReceiver());
        assertEquals(expected.getType(),actual.getType());
    }
}