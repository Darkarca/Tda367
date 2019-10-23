package com.CEYMChatClient.Controller;

import com.CEYMChatLib.UserInfo;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class FriendListItemTest {
    UserInfo testInfo;

    FriendListItem testItem;

    @Before
    public void setup() {
        testItem = new FriendListItem();
        testInfo = new UserInfo();

        testItem.setUInfo(testInfo);
    }

    @Test
    public void toggleFriend() {
        assertEquals(testItem.getUInfo().getIsFriend(), false);
        testItem.toggleFriend();
        assertEquals(testItem.getUInfo().getIsFriend(), true);
        testItem.toggleFriend();
        assertEquals(testItem.getUInfo().getIsFriend(), false);
    }

}