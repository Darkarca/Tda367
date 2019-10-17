package com.CEYMChatClient.Services;

import com.CEYMChatClient.ClientMain;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.RemoteServices.InputService;
import com.CEYMChatClient.Services.RemoteServices.OutputService;
import com.CEYMChatLib.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.*;
import java.net.Socket;

public class InputServiceTest {

   // private static InputService testService;
   // private static ClientModel testModel;
   // private static OutputStream testOOS;

    //Byte array to store the message to be sent
    ByteArrayOutputStream toBeSent;
    //To write the object to the array
    ObjectOutputStream oos;

    //Byte array to store the message being captured
    ByteArrayOutputStream toBeWritten;
    //To write the object to the array
    ObjectOutputStream oos1;

    @Mock
    ClientModel clientModel;

    @Mock
    ObjectInputStream objectInputStream;

    @Mock
    Socket socket;

    @InjectMocks
    InputService inputService;

    @Captor
    private ArgumentCaptor<byte[]> valueCapture;

    InputStream myInputStream;

    @Before
    public void setup() throws IOException {
        // Socket s = new Socket();
        //testModel = new ClientModel();
        //testService = new InputService(testModel, s);
        //testOOS = s.getOutputStream();
        myInputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        MockitoAnnotations.initMocks(this);
        toBeSent = new ByteArrayOutputStream();
        toBeWritten = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(toBeSent);
            oos1 = new ObjectOutputStream(toBeWritten);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //inject mocked output stream into InputService class
        try {
            FieldSetter.setField(inputService, inputService.getClass()
                    .getDeclaredField("socket"), socket);
            FieldSetter.setField(inputService, inputService.getClass().
                    getDeclaredField("messageInStream"), objectInputStream);
           // FieldSetter.setField(socket,socket.getClass().getDeclaredField(""));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("No Such Field");
        }
        //inputService.connectToServer();
    }

    @Test
    public void testDisconnect() throws IOException {

        Mockito.when(socket.getInputStream()).thenReturn(myInputStream);
        //Mockito.verify(myInputStream).read(valueCapture.capture());
        //Mockito.verify(myInputStream).read(valueCapture.capture());
        //byte[] readData = valueCapture.getValue();
        inputService.disconnect();
        assertEquals(socket.isConnected(), false);
    }

}
