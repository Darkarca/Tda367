package com.CEYMChatServer;

import com.CEYMChatServer.Model.ServerModel;
import com.CEYMChatServer.Services.SocketHandler;
import org.junit.*;

public class TestServer {
    @Test
    public void testLogin(){
    }

    @Test
    public void checkUser(){
    }

    @Test
    public void testSocketHandlerStart(){
        SocketHandler handler = new SocketHandler(new ServerModel());
        handler.start();

    }
    @Test
    public void testAddUser(){

    }

}
