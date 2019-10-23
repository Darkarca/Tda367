package com.CEYMChatClient.Model;

import com.CEYMChatClient.Services.FileServices.ILoadMessages;
import com.CEYMChatClient.Services.FileServices.ISaveMessages;
import com.CEYMChatClient.Services.FileServices.LoadFromCSV;
import com.CEYMChatClient.Services.FileServices.SaveToCSV;
import com.CEYMChatLib.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ClientModelTest {

    private static ClientModel model = new ClientModel();
    static private List<Message<String>> testSentList = new ArrayList<>();
    private static ISaveMessages testSaver = new SaveToCSV();
    private static ILoadMessages testLoader = new LoadFromCSV();
    private static List<Message<String>> testReceivedList = new ArrayList<>();
    private static List<String> sentTestMsgs = new ArrayList<>();
    private static List<String> receivedTestMsgs = new ArrayList<>();
    private static UserInfo testUserInfo1 = new UserInfo();
    private static UserInfo testUserInfo2 = new UserInfo();
    private static List<UserInfo> userList = new ArrayList<>();

    /**
     * creates a virtual saved messages
     */
    @BeforeClass
    static public void mockUpSavedMessages() throws IOException {
        sentTestMsgs.add("Hello World");
        sentTestMsgs.add("Hello World2");
        receivedTestMsgs.add("Hello World3");
        receivedTestMsgs.add("Hello World4");
        testUserInfo1.setUsername("test1");
        testUserInfo2.setUsername("test2");
        userList.add(testUserInfo1);
        model.setUInfo(testUserInfo2);
        model.setUserList(userList);
        model.addFriends(testUserInfo2);

        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World", testUserInfo1, "test2"));
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World2", testUserInfo1, "test2"));
        model.addMessage(MessageFactory.createStringMessage("Hello World3", model.getUInfo(), "test1"));
        model.addMessage(MessageFactory.createStringMessage("Hello World4", model.getUInfo(), "test1"));
        testSaver.saveMessages(model.getReceivedMessages(),model.getSentMessages(),model.getUsername());
        model.login();
    }

    /**
     * saves the virtual messages to a certain file locally
     */
    @Test
    public void saveMessagesToFile() {
        ISaveMessages testSaver = new SaveToCSV();
        try {
            ((SaveToCSV) testSaver).saveArrayListToFile(testSentList,"/messages/test.csv", model.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File testFile = new File("messages/test.csv");
        boolean exists = testFile.exists();
        assertTrue("MessageFile created successfully", exists);
    }


    /**
     * loads the saved sended messages
     */
    @Test
    public void loadSavedSentMessage() throws IOException {
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        String[] expectedArray = {"Me: ", "Hello World3", "Me: ", "Hello World4"};
        expected.addAll(Arrays.asList(expectedArray));
        ILoadMessages testLoader = new LoadFromCSV();
        actual = testLoader.loadSavedMessages("messages/sent.csv");
        assertEquals("Loaded messages doesn't match expected value", expected, actual);

        //Overwrites the test-files
        FileWriter writer = new FileWriter("messages/sent.csv");
        writer.write("");
    }


    /**
     * loads the saved recieved messages
     */
    @Test
    public void loadSavedReceivedMessage() throws IOException {
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        String[] expectedArray = {"test1: ", "Hello World", "test1: ", "Hello World2"};
        expected.addAll(Arrays.asList(expectedArray));
        actual = testLoader.loadSavedMessages("messages/received.csv");
        assertEquals("Loaded messages doesn't match expected value", expected, actual);

        //Overwrites the test-files
        FileWriter writer = new FileWriter("messages/received.csv");
        writer.write("");

    }

    @Test
    public void combineSavedLists(){
        List<String> allTestMsgs = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        model.combineSavedLists(sentTestMsgs,receivedTestMsgs,allTestMsgs);
        for (String s:sentTestMsgs) {
            expected.add(s);
        }
        for (String s:receivedTestMsgs) {
            expected.add(s);
        }
        assertEquals(allTestMsgs.size(),expected.size());
    }

    @Test
    public void messageIsOfStringType(){
       Message m1 = MessageFactory.createStringMessage("String",model.getUInfo(),model.getUsername());
       Message m2 = MessageFactory.createCommandMessage(new Command(CommandName.REFRESH_FRIENDLIST,"refresh"),testUserInfo1);
       Message m3 = new Message(new String());

        assertEquals(model.messageIsOfStringType(m1),true);
        assertNotEquals(model.messageIsOfStringType(m2),true);
        assertEquals(model.messageIsOfStringType(m3),true);
    }

    @Test
    public void isMuted(){
        assertEquals(model.isMuted(testUserInfo2),false);
        model.addMuted(testUserInfo2);
        assertEquals(model.isMuted(testUserInfo2),true);
        model.removeMuted(testUserInfo2);
        assertEquals(model.isMuted(testUserInfo2),false);
    }

    @Test
    public void isBlocked(){
        assertEquals(model.isBlocked(testUserInfo2),false);
        assertEquals(model.isBlocked(testUserInfo1),false);
        model.addBlockedFriend(model.getUInfo());
        model.addBlockedFriend(testUserInfo2);
        model.addBlockedFriend(testUserInfo1);
        assertEquals(model.isBlocked(testUserInfo2),false);
        assertEquals(model.isBlocked(testUserInfo1),true);
        assertNotEquals(model.getFriendList().contains(testUserInfo1),true);
    }
}