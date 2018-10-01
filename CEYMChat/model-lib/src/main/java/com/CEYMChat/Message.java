package com.CEYMChat;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;

/**
 * Generic class that works in conjunction with MessageFactory to create messages that are sent and received by the server/client.
 * @param <T>
 */

public class Message<T> implements Serializable {
    //User sender;
    //User receiver;
    T data;


    protected Message(T data){
        this.data = data;
    }



    public T getData(){

        return this.data;
    }

}
