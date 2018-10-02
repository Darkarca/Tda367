package com.CEYMChat;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Generic class that works in conjunction with MessageFactory to create messages that are sent and received by the server/client.
 * @param <T>
 */

public class Message<T> implements Serializable {
    String sender;

    //User receiver;
    T data;

    protected Message(T data, String sender){
        this.data = data;
        this.sender = sender;
    }



    public String getSender() {
        return sender;
    }

    public T getData(){

        return this.data;
    }

    public Class getType(){
       return data.getClass();
    }

}
