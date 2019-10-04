package com.CEYMChatClient.Controller;

import com.CEYMChatLib.UserInfo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FriendListItemTest {
    FriendListItem testItem;
    UserInfo testInfo;

    @Before
    public void setup(){
    testInfo = new UserInfo();
    testItem = new FriendListItem();
    testItem.setUInfo(testInfo);
    }

    @Test
    public void toggleFriend(){
        assertEquals(testItem.getUInfo().getIsFriend(),false);
        //testItem.toggleFriend();
        //assertEquals(testItem.getUInfo().getIsFriend(),true);
    }
}
