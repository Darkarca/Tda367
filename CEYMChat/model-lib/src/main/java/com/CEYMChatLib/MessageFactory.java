package com.CEYMChatLib;
import javafx.scene.image.Image;
import java.io.File;
import java.util.List;
/**
 * Factory for creating Message(s) of predefined types.
 * To make sure messages only hold certain types of data
 */
public abstract class MessageFactory {


    public static Message<String> createStringMessage(String data, UserDisplayInfo sender, String receiver){
        return new Message(data, sender, receiver);
    }

    public static Message<Image> createImageMessage(Image data, UserDisplayInfo sender, String receiver){
        return new Message(data, sender, receiver);
    }

    public static Message<File> createFileMessage(MessageFile data, UserDisplayInfo sender, String receiver) {
        return new Message(data, sender, receiver);
    }

    public static Message<Command> createCommandMessage(Command data, UserDisplayInfo sender){
        return new Message(data, sender);
    }

    public static Message<List> createFriendInfoList (List<UserDisplayInfo> data,UserDisplayInfo sender, String receiver){
        return new Message(data, sender, receiver);
    }
}
