package com.CEYMChat;
import javafx.scene.image.Image;
import java.io.File;
import java.util.List;
/**
 * Factory for creating Message(s) of predefined types.
 * To make sure messages only hold certain types of data
 */
public class MessageFactory {

    /**
     *
     * @param data
     * @param user
     * @param receiver
     * @return
     */
    public static Message<String> createStringMessage(String data, String user, String receiver){
        return new Message(data, user, receiver);
    }

    /**
     *
     * @param data
     * @param user
     * @param receiver
     * @return
     */
    public static Message<Image> createImageMessage(Image data, String user, String receiver){
        return new Message(data, user, receiver);
    }

    /**
     *
     * @param data
     * @param user
     * @param receiver
     * @return
     */
    public static Message<File> createFileMessage(File data, String user, String receiver) {
        return new Message(data, user, receiver);
    }

    /**
     *
     * @param data
     * @param user
     * @return
     */
    public static Message<Command> createCommandMessage(Command data, String user){
        return new Message(data, user);
    }

    /**
     *
     * @param data
     * @param user
     * @param receiver
     * @return
     */
    public static Message<List> createFriendInfoList (List<UserDisplayInfo> data,String user, String receiver){
        return new Message(data, user, receiver);
    }
}
