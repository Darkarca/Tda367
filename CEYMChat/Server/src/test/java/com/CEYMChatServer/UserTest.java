package com.CEYMChatServer;

import com.CEYMChatLib.Message;
import com.CEYMChatLib.MessageFactory;
import com.CEYMChatLib.UserInfo;
import com.CEYMChatServer.Model.User;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void syncFriends() {
        List<UserInfo> testList = new ArrayList<>();
        UserInfo testUInfo = new UserInfo();
        testUInfo.setUsername("true");
        testList.add(testUInfo);
        User testUser = new User();
        UserInfo testUserUInfo = new UserInfo();
        Message<List> testMessage = MessageFactory.createUsersDisplayInfoMessages(testList,testUser.getUInfo(),"testUser");
        testUserUInfo.setUsername("testUser");
        testUser.setuInfo(testUserUInfo);
        testUser.syncFriends(testMessage);
        assertEquals("User added to friendslist","true",testUser.getFriendsInfo().get(0).getUsername());
    }

    @Test
    public void checkFriends() {
        List<UserInfo> testList = new ArrayList<>();
        UserInfo testUInfo = new UserInfo();
        UserInfo shouldAddInfo = new UserInfo();
        testUInfo.setUsername("true");
        shouldAddInfo.setUsername("false");
        testList.add(testUInfo);
        //testList.add(willNotAddInfo);
        User testUser = new User();
        testUser.setuInfo(new UserInfo());
        Message<List> testMessage = MessageFactory.createUsersDisplayInfoMessages(testList, testUser.getUInfo(), "testUser");
        testUser.getUInfo().setUsername("testUser");
        testUser.addFriends(shouldAddInfo);
        assertEquals("User sent in message found in friendslist",testUInfo,((List<UserInfo>)testUser.checkFriends(testMessage).getData()).get(0));
        assertEquals("User added manually added successfully",shouldAddInfo,((List<UserInfo>)testUser.checkFriends(testMessage).getData()).get(1));
        assertEquals("User already in list was not added",2,((List<UserInfo>)testUser.checkFriends(testMessage).getData()).size());


    }

}