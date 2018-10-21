package com.CEYMChatClient.Model;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.Message;
import com.CEYMChatLib.MessageFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClientModelTest {

    static ClientModel model = new ClientModel();
   static private ArrayList<Message> testList = new ArrayList<>();


    /** creates a virtual saved messages */
    @BeforeClass
   static public void mockUpSavedMessages(){
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World","test1","test2"));
        model.addReceivedMessage(MessageFactory.createStringMessage("Hello World2","test1","test2"));
        model.addSentMessage(MessageFactory.createStringMessage("Hello World3","test2","test1"));
        model.addSentMessage(MessageFactory.createStringMessage("Hello World4","test2","test1"));
        testList.add(MessageFactory.createStringMessage("Hello World","test1","test2"));
        testList.add(MessageFactory.createStringMessage("Hello World2","test1","test2"));
        testList.add(MessageFactory.createStringMessage("Hello World3","test2","test1"));
        testList.add(MessageFactory.createStringMessage("Hello World4","test2","test1"));
        model.saveSendMessages("messages/sent.csv");
        model.saveReceivedMessages("messages/received.csv");

    }

    /** saves the virtual messages to a certain file locally */
    @Test
    public void saveMessagesToFile() {
        try {
            model.saveArrayListToFile(testList,"messages/test.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File testFile = new File("messages/test.csv");
        boolean exists = testFile.exists();
        assertTrue(exists);
    }


    /** saves the received messages to a certain files */
    @Test
    public void saveReceivedMessages() {
        model.saveReceivedMessages("messages/received.csv");
        //Passes if no exception
    }


    /** saves and sends the messages */
    @Test
    public void saveSendMessages() {
        model.saveSendMessages("messages/sent.csv");
        //Passes if no exception

    }

    /** loads the saved sended messages */
    @Test
    public void loadSavedSentMessage() throws IOException {
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual = new ArrayList<>();
        String[] expectedArray = {"test2", "Hello World3", "test2", "Hello World4"};
        expected.addAll(Arrays.asList(expectedArray));
        actual = model.loadSavedMessages("messages/sent.csv");
        assertEquals(expected,actual);

    }


    /** loads the saved recieved messages */
    @Test
    public void loadSavedReceivedMessage() throws IOException {
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual = new ArrayList<>();
        String[] expectedArray = {"test1", "Hello World", "test1", "Hello World2"};
        expected.addAll(Arrays.asList(expectedArray));
        actual = model.loadSavedMessages("messages/received.csv");
        assertEquals(expected,actual);

    }
}