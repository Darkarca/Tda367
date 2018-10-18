package com.CEYMChat;

import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;

public class SocketHandlerTest {

    ServerModel testModel = new ServerModel();
    SocketHandler testHandler = new SocketHandler(testModel);

    @Test
    public void start() {
        int expected = java.lang.Thread.activeCount()+1;
        testHandler.start();
        assertEquals(expected,java.lang.Thread.activeCount());
    }

    @Test
    public void connectSocket() throws IOException {
        testHandler.start();
        User testUser = new User();
        testUser.setUsername("testUser");
        Socket socket = new Socket("localhost", 9000);
        testUser.initThreads(socket, testModel);
        assertEquals(1, testModel.getUserList().size());
    }
}