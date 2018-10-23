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
        Message<List> testMessage = MessageFactory.createFriendInfoList(testList,"testUser","testUser");
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.syncFriends(testMessage);
        assertEquals("User added to friendslist","true",testUser.getFriendsInfo().get(0).getUsername());
    }

    @Test
    public void checkFriends() {
        List<UserDisplayInfo> testList = new ArrayList<>();
        UserDisplayInfo testUInfo = new UserDisplayInfo();
        UserDisplayInfo shouldAddInfo = new UserDisplayInfo();
        testUInfo.setUsername("true");
        shouldAddInfo.setUsername("false");
        testList.add(testUInfo);
        //testList.add(willNotAddInfo);
        Message<List> testMessage = MessageFactory.createFriendInfoList(testList, "testUser", "testUser");
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.addFriends(shouldAddInfo);
        assertEquals("User sent in message found in friendslist",testUInfo,((List<UserDisplayInfo>)testUser.checkFriends(testMessage).getData()).get(0));
        assertEquals("User added manually added successfully",shouldAddInfo,((List<UserDisplayInfo>)testUser.checkFriends(testMessage).getData()).get(1));
        assertEquals("User already in list was not added",2,((List<UserDisplayInfo>)testUser.checkFriends(testMessage).getData()).size());


    }

}