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
        SocketHandler sh = new SocketHandler(new ServerModel());
        sh.start();

    }
    @Test
    public void testAddUser(){

    }

}
