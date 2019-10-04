package com.CEYMChatClient.Services;

import com.CEYMChatClient.ClientMain;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.RemoteServices.InputService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class InputServiceTest {

    private static InputService testService;
    private static ClientModel testModel;
    private static OutputStream testOOS;

    @Before
    public void setup() throws IOException {
        Socket s = new Socket();
        testModel = new ClientModel();
        testService = new InputService(testModel, s);
        testOOS = s.getOutputStream();
    }

}
