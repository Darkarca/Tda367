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
     * creates a virtual saved messages both in the model and saved locally on the machine
     * Also sets a few inital values in model by adding a friend aswell as setting the models UserInfo
     */
    @BeforeClass
    static public void mockUpSavedMessages() throws IOException {
        // Creates fake messages
        sentTestMsgs.add("Hello World");
        sentTestMsgs.add("Hello World2");
        receivedTestMsgs.add("Hello World3");
        receivedTestMsgs.add("Hello World4");

        // Creates fake UserInfo objects
        testUserInfo1.setUsername("test1");
        testUserInfo2.setUsername("test2");
        userList.add(testUserInfo1);

        // Sets values in the model
        model.setUInfo(testUserInfo2);
        model.setUserList(userList);
        model.addFriends(testUserInfo2);

        // Updates model with the fake messages
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World", testUserInfo1, "test2"));
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World2", testUserInfo1, "test2"));
        model.addMessage(MessageFactory.createStringMessage("Hello World3", model.getUInfo(), "test1"));
        model.addMessage(MessageFactory.createStringMessage("Hello World4", model.getUInfo(), "test1"));

        // Saves messages locally
        testSaver.saveMessages(model.getReceivedMessages(),model.getSentMessages(),model.getUsername());
        model.login();
    }

    /**
     * saves the virtual messages to a certain file locally
     * Fails if testSaver does not create a new file called "test.csv", does not check what is inside the
     * file since loader checks that by nature
     */
    @Test
    public void saveMessagesToFile() {
        ISaveMessages testSaver = new SaveToCSV();
        // Creates a file object and tries to delete any existing testfile
        File testFile = new File(System.getProperty("user.dir") + "/messages/test.csv");
        testFile.delete();
        assertFalse(testFile.exists());
        // Saves messages from clientModel locally
        try {
            ((SaveToCSV) testSaver).saveArrayListToFile(testSentList,System.getProperty("user.dir") + "/messages/test.csv", model.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Checks that a new file has been created
        boolean exists = testFile.exists();
        assertTrue("MessageFile created successfully", exists);
    }


    /**
     * loads the saved sent messages
     * Fails if loaded messages does not equal to what was expected to be saved in setup()
     * Specifically checks for 'sent' messages (Messages where the sender == the models UserInfo)
     */
    @Test
    public void loadSavedSentMessage() throws IOException {
        // Create fake messages to compare to, sligthly different than those we created in setup()
        // as sent messages get the username replaced during saving
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        String[] expectedArray = {"Me: ", "Hello World3", "Me: ", "Hello World4"};
        expected.addAll(Arrays.asList(expectedArray));
        // Loads messages from "sent.csv" and checks that they are the same as expected
        ILoadMessages testLoader = new LoadFromCSV();
        actual = testLoader.loadSavedMessages("messages/sent.csv");
        assertEquals("Loaded messages doesn't match expected value", expected, actual);

        //Overwrites the test-files
        FileWriter writer = new FileWriter("messages/sent.csv");
        writer.write("");
    }


    /**
     * loads the saved recieved messages
     * Fails if the loaded messages does not equal to what was expected to be saved in setup()
     * Specifically checks for 'received' messages (Messages where the sender != the models UserInfo)
     */
    @Test
    public void loadSavedReceivedMessage() throws IOException {
        // Create fake messages to compare to, should be equal to what was created during setup()
        // (Could potentially be loaded directly from the model at this point)
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        String[] expectedArray = {"test1: ", "Hello World", "test1: ", "Hello World2"};
        expected.addAll(Arrays.asList(expectedArray));
        // Loads messages from "received.csv" and checks that they are the same as expected
        actual = testLoader.loadSavedMessages("messages/received.csv");
        assertEquals("Loaded messages doesn't match expected value", expected, actual);

        //Overwrites the test-files
        FileWriter writer = new FileWriter("messages/received.csv");
        writer.write("");

    }

    /**
     * Tests that combining two lists work as intended
     * Fails if the length of the combined list is different to the length of the expected list
     * Fails if the message at (1) in both lists are different
     */
    @Test
    public void combineSavedLists(){
        // Create an expected list
        List<String> allTestMsgs = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        for (String s:sentTestMsgs) {
            expected.add(s);
        }
        for (String s:receivedTestMsgs) {
            expected.add(s);
        }
        // Combine two lists to assert that the lists are equal
        model.combineSavedLists(sentTestMsgs,receivedTestMsgs,allTestMsgs);
        assertEquals(allTestMsgs.size(),expected.size());
        assertEquals(allTestMsgs.get(1),expected.get(1));
    }

    /**
     * Tests that messages of different types return the correct value from messageIsOfStringType
     * Fails if a message returns an unexpected value
     */
    @Test
    public void messageIsOfStringType(){
        // Creates fake messages to test
       Message m1 = MessageFactory.createStringMessage("String",model.getUInfo(),model.getUsername());
       Message m2 = MessageFactory.createCommandMessage(new Command(CommandName.REFRESH_FRIENDLIST,"refresh"),testUserInfo1);
       Message m3 = new Message(new String());
        // Tests against expected values
        assertEquals(model.messageIsOfStringType(m1),true);
        assertNotEquals(model.messageIsOfStringType(m2),true);
        assertEquals(model.messageIsOfStringType(m3),true);
    }

    /**
     * Checks that a muted user is added and then removes from the muted lists, very trivial test
     * Fails if a user cannot be added and then removed from the mutedlist
     */
    @Test
    public void isMuted(){
        assertEquals(model.isMuted(testUserInfo2),false);
        model.addMuted(testUserInfo2);
        assertEquals(model.isMuted(testUserInfo2),true);
        model.removeMuted(testUserInfo2);
        assertEquals(model.isMuted(testUserInfo2),false);
    }

    /**
     * Checks that a blocked user is added to the blocked list, checks that the user cannot add him/herself to the blockedlist
     * Fails if the user him/herself can be added to the blocked list
     * Fails if another user cannot be added to the blocked list
     * Fails if any user is in the blocked list initially
     */
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