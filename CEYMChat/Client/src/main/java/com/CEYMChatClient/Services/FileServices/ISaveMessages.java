package com.CEYMChatClient.Services.FileServices;

import com.CEYMChatLib.Message;

import java.io.IOException;
import java.util.List;

public interface ISaveMessages {

    void saveMessages(List<Message<String>> sentMessages, List<Message<String>> receivedMessages, String username);
    void saveArrayListToFile(List<Message<String>> list, String filename, String username) throws IOException;
    void saveReceivedMessages(List<Message<String>> receivedMessages, String filename, String username);
    void saveSendMessages(List<Message<String>> sentMessages, String filename, String username);


}
