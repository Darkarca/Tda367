package com.CEYMChat;
import javafx.scene.image.Image;
import java.io.File;


/**
 * Factory for creating Message(s) of predefined types.
 */
public class MessageFactory {


    public static Message<String> createStringMessage(String data){
        return new Message(data);
    }
    public static Message<Image> createImageMessage(Image data){
        return new Message(data);
    }
    public static Message<File> createFileMessage(File data) {
        return new Message(data);
    }
    public static Message<Command> createCommandMessage(Command data){
        return new Message(data);
    }
}
