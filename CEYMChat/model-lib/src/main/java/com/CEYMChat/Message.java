package com.CEYMChat;

import javafx.scene.image.Image;

import java.io.File;

public class Message<T>{
    T data;

    protected Message(T data){
        this.data = data;
    }

}
