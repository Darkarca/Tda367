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
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World", testUserInfo1, "test2"));
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World2", testUserInfo1, "test2"));
        model.addSentMessage(MessageFactory.createStringMessage("Hello World3", testUserInfo2, "test1"));
        model.addSentMessage(MessageFactory.createStringMessage("Hello World4", testUserInfo2, "test1"));
        testReceivedList.add(MessageFactory.createStringMessage("Hello World", testUserInfo1, "test2"));
        testReceivedList.add(MessageFactory.createStringMessage("Hello World2", testUserInfo1, "test2"));
        testSentList.add(MessageFactory.createStringMessage("Hello World3", testUserInfo2, "test1"));
        testSentList.add(MessageFactory.createStringMessage("Hello World4", testUserInfo2, "test1"));
        testSaver.saveSendMessages(testSentList,"messages/sent.csv",model.getUsername());
        testSaver.saveReceivedMessages(testReceivedList,"messages/received.csv",model.getUsername());
    }

    /**
     * saves the virtual messages to a certain file locally
     */
    @Test
    public void saveMessagesToFile() {
        ISaveMessages testSaver = new SaveToCSV();
        try {
            ((SaveToCSV) testSaver).saveArrayListToFile(testSentList,"messages/test.csv", model.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File testFile = new File("messages/test.csv");
        boolean exists = testFile.exists();
        assertTrue("MessageFile created successfully", exists);
    }


    /**
     * saves the received messages to a certain files
     */
    @Test
    public void saveReceivedMessages() throws IOException {
        // model.saveReceivedMessages("messages/received.csv");
        //Passes if no exception

    }


    /**
     * saves and sends the messages
     */
    @Test
    public void saveSendMessages() throws FileNotFoundException {
        //model.saveSendMessages("messages/sent.csv");
        //Passes if no exception
        //List<String> expected = new ArrayList<>();
        //List<String> actual = new ArrayList<>();
        //FileReader reader = new FileReader("messages/sent.csv");
        //String[] expectedArray = {"test2: ", "Hello World3", "test2: ", "Hello World4"};
        //actual = reader.
        //expected.addAll(Arrays.asList(expectedArray));
        //assertEquals(expected, actual);

    }

    /**
     * loads the saved sended messages
     */
    @Test
    public void loadSavedSentMessage() throws IOException {
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        String[] expectedArray = {"test2: ", "Hello World3", "test2: ", "Hello World4"};
        expected.addAll(Arrays.asList(expectedArray));
        ILoadMessages testLoader = new LoadFromCSV();
        actual = testLoader.loadSavedMessages("messages/sent.csv");
        //actual = model.loadSavedMessages("messages/sent.csv");
        assertEquals("Loaded messages match expected value", expected, actual);

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
        //actual = model.loadSavedMessages("messages/received.csv");
        assertEquals("Loaded messages match expected value", expected, actual);

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
       Message m1 = MessageFactory.createStringMessage("String",testUserInfo1,"test2");
       Message m2 = MessageFactory.createCommandMessage(new Command(CommandName.REFRESH_FRIENDLIST,"refresh"),testUserInfo1);
       Message m3 = new Message(new String());
        assertEquals(m1.getType(), "".getClass());
        assertNotEquals(m2.getType(),"".getClass());
        assertEquals(m3.getType(),"".getClass());
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
        model.addBlockedFriend(testUserInfo2);
        assertEquals(model.isBlocked(testUserInfo2),true);
    }
}