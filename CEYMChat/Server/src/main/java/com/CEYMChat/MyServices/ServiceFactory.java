package com.CEYMChat.MyServices;

import com.CEYMChat.MessageFactory;

import java.net.Socket;

public class ServiceFactory {
    public static IService createTextProtocol(Socket client1, Socket client2){
        return new TextSessionHandler(client1,client2);
    }


}
