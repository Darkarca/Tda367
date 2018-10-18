package com.CEYMChat;

import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;

public class SocketHandlerTest {

    ServerModel testModel;
    SocketHandler testHandler;

    @Test
    public void start() throws IOException {
         testModel = new ServerModel();
         testHandler = new SocketHandler(testModel);
        int expected = java.lang.Thread.activeCount()+1;
        testHandler.start();
        assertEquals(expected,java.lang.Thread.activeCount());
        testModel.getServerSocket().close();
    }

    @Test
    public void connectSocket() throws IOException, InterruptedException {
        testModel = new ServerModel();
        testHandler = new SocketHandler(testModel);
        Socket socket = new Socket("localhost", 9000);
        Thread.sleep(2000);
        testHandler.start();
        Thread.sleep(2000);
        assertEquals(1, testModel.getUserList().size());
        testModel.getServerSocket().close();
    }
}