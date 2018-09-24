package com.CEYMChat;
import javafx.scene.image.Image;
import java.io.File;

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
}
