package com.CEYMChatClient.Services.FileServices;

import com.CEYMChatLib.Message;

import java.io.IOException;
import java.util.List;

public interface ISaveMessages {

    public void saveMessages(List<Message<String>> sentMessages, List<Message<String>> receivedMessages, String username);


}
