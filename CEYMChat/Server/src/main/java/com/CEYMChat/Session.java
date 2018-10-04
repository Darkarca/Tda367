package com.CEYMChat;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Session implements Runnable{


    User user1;
    User user2;




    public Session(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;

    }


    @Override
    public void run() {
        while (true){
            user2.getWriter().setOutMessage(user1.getReader().getInMessage());
            user1.getWriter().setOutMessage(user2.getReader().getInMessage());

        }


    }
}
