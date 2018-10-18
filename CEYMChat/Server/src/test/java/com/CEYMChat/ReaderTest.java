package com.CEYMChat;

import com.CEYMChat.Services.IReader;
import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;


public class ReaderTest {

    IReader testReader = new Reader(new ServerModel(), new Socket("localhost", 9000));

    public ReaderTest() throws IOException {
    }

    @Test
    public void run() {

    }

}