package com.CEYMChatClient.Services;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.RemoteServices.InputService;
import com.CEYMChatLib.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class InputServiceTest {

    ByteArrayInputStream readValue;
    ByteArrayInputStream toBeRead;

    @Mock
    ClientModel clientModel;

    @Mock
    ObjectInput objectInput;

    @Mock
    Socket socket;

    @InjectMocks
    InputService inputService;

    InputStream myInputStream;

    UserInfo uInfo;




    @Before
    public void setup() throws IOException {

        myInputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        MockitoAnnotations.initMocks(this);
        readValue = new ByteArrayInputStream(new byte[2]);
        toBeRead = new ByteArrayInputStream(new byte[2]);

        //inject mocked output stream into InputService class
        try {
            FieldSetter.setField(inputService, inputService.getClass()
                    .getDeclaredField("socket"), socket);
            FieldSetter.setField(inputService, inputService.getClass().
                    getDeclaredField("messageInStream"), objectInput);
            FieldSetter.setField(inputService,inputService.getClass().getDeclaredField("model"),clientModel);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("No Such Field");
        }
        uInfo = new UserInfo();
    }

    @Test
    public void testDisconnect(){
        inputService.disconnect();
        assertEquals(socket.isConnected(), false);
        inputService.checkConnectionIsLive();
        Mockito.verify(socket,atLeast(2)).isConnected();
    }

    @Test
    public synchronized void testReadString() throws IOException, ClassNotFoundException, InterruptedException {
        Message message = MessageFactory.createStringMessage("hi", uInfo, "Erik");
        Mockito.when(objectInput.readObject()).thenReturn(message);
        inputService.read();
        wait(100);
        inputService.disconnect();
        assertEquals(inputService.getMessageIn().getData().toString(),message.getData().toString());
        Mockito.verify(objectInput,atLeast(1)).readObject();
    }

    @Test
    public synchronized void testReadList() throws IOException, ClassNotFoundException, InterruptedException {
        Message message = new Message(new ArrayList<>(),uInfo);
        Mockito.when(objectInput.readObject()).thenReturn(message);
        inputService.read();
        wait(100);
        inputService.disconnect();
        assertEquals(inputService.getMessageIn().getData().getClass(),message.getData().getClass());

    }

    @Test
    public synchronized void testReadFile() throws IOException, ClassNotFoundException, InterruptedException {
        MessageFile file = new MessageFile(new File("InputServiceTest.java"));
        Message message = MessageFactory.createFileMessage(file,uInfo,"Erik");
        Mockito.when(objectInput.readObject()).thenReturn(message);
        inputService.read();
        wait(100);
        inputService.disconnect();
        assertEquals(inputService.getMessageIn().getReceiver(),message.getReceiver());
    }
    @Test
    public synchronized void testReadCommand() throws IOException, ClassNotFoundException, InterruptedException {
        Message message = MessageFactory.createCommandMessage(new Command(CommandName.DISCONNECT,"disconnect"),uInfo);
        Mockito.when(objectInput.readObject()).thenReturn(message);
        inputService.read();
        wait(100);
        assertEquals(((Command)(inputService.getMessageIn().getData())).getCommandName(),((Command)message.getData()).getCommandName());
    }

}
