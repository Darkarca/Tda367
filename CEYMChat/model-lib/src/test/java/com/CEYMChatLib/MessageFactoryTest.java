package com.CEYMChatLib;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MessageFactoryTest {

    @Test
    public void createStringMessage() {
        Message expected =  new Message<>("test1","test2","test3");
        Message actual = MessageFactory.createStringMessage("test1","test2","test3");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getReceiver(),actual.getReceiver());
        assertEquals(expected.getType(),actual.getType());
    }

    @Test
    public void createFileMessage() {
        MessageFile file = new MessageFile(new File(""));
        Message expected =  new Message<>(file,"test2","test3");
        Message actual = MessageFactory.createFileMessage(file,"test2","test3");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getReceiver(),actual.getReceiver());
        assertEquals(expected.getType(),actual.getType());
    }

    @Test
    public void createCommandMessage() {
        Command command = new Command(CommandName.SET_USER,"test");
        Message expected =  new Message<>(command,"test2","test3");
        Message actual = MessageFactory.createCommandMessage(command,"test2");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getType(),actual.getType());
    }

    @Test
    public void createFriendInfoList() {
        List<UserDisplayInfo> friendInfoList = new ArrayList<>();
        friendInfoList.add(new UserDisplayInfo());
        Message expected =  new Message<>(friendInfoList,"test2","test3");
        Message actual = MessageFactory.createUsersDisplayInfoMessages(friendInfoList,"test2","test3");
        assertEquals(expected.getData(),actual.getData());
        assertEquals(expected.getSender(),actual.getSender());
        assertEquals(expected.getReceiver(),actual.getReceiver());
        assertEquals(expected.getType(),actual.getType());
    }
}