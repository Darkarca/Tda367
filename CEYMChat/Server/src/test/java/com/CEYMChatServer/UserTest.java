package com.CEYMChatServer;

import com.CEYMChatLib.Message;
import com.CEYMChatLib.MessageFactory;
import com.CEYMChatLib.UserDisplayInfo;
import com.CEYMChatServer.Model.User;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void syncFriends() {
        List<UserDisplayInfo> testList = new ArrayList<>();
        UserDisplayInfo testUInfo = new UserDisplayInfo();
        testUInfo.setUsername("true");
        testList.add(testUInfo);
        Message testMessage = MessageFactory.createFriendInfoList(testList,"testUser","testUser");
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.syncFriends(testMessage);
        assertEquals("true",testUser.getFriends().get(0).getUsername());
    }

    @Test
    public void checkFriends() {
        List<UserDisplayInfo> testList = new ArrayList<>();
        UserDisplayInfo testUInfo = new UserDisplayInfo();
        UserDisplayInfo willAddInfo = new UserDisplayInfo();
        testUInfo.setUsername("true");
        willAddInfo.setUsername("false");
        testList.add(testUInfo);
        //testList.add(willNotAddInfo);
        Message testMessage = MessageFactory.createFriendInfoList(testList, "testUser", "testUser");
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.addFriends(willAddInfo);
        assertEquals(testUInfo,((List<UserDisplayInfo>)testUser.checkFriends(testMessage).getData()).get(0));
        assertEquals(willAddInfo,((List<UserDisplayInfo>)testUser.checkFriends(testMessage).getData()).get(1));
        assertEquals(2,((List<UserDisplayInfo>)testUser.checkFriends(testMessage).getData()).size());


    }

    @Test
    public void addFriends() {
    }

    @Test
    public void removeFriends() {

    }
}