package com.CEYMChat;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServerMainTest {
        ServerMain testMain = new ServerMain();
    @Test
    public void start() {
        int expected = java.lang.Thread.activeCount()+1;
        testMain.startHandler();
        assertEquals(expected,java.lang.Thread.activeCount());
    }
}