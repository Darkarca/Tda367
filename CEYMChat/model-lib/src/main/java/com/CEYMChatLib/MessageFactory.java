package com.CEYMChatLib;
import java.io.File;
import java.util.List;
/**
 * Factory for creating Message(s) of predefined types.
 * To make sure messages only hold certain types of data
 */
public abstract class MessageFactory {


    public static Message<String> createStringMessage(String data, UserInfo sender, String receiver){
        return new Message(data, sender, receiver);
    }

    public static Message<File> createFileMessage(MessageFile data, UserInfo sender, String receiver) {
        return new Message(data, sender, receiver);
    }

    public static Message<Command> createCommandMessage(Command data, UserInfo sender){
        return new Message(data, sender);
    }

    public static Message<List> createUsersDisplayInfoMessages(List<UserInfo> data, UserInfo sender, String receiver){
        return new Message(data, sender, receiver);
    }
}
