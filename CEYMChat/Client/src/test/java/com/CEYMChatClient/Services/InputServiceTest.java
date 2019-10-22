package com.CEYMChatClient.Services;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.FileServices.Configurations;
import com.CEYMChatClient.Services.RemoteServices.InputService;
import com.CEYMChatClient.Services.RemoteServices.ServiceFactory;
import com.CEYMChatLib.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class InputServiceTest {

   // private static InputService testService;
   // private static ClientModel testModel;
   // private static OutputStream testOOS;

    //Byte array to store the message to be sent
    ByteArrayInputStream readValue;
    //To write the object to the array
    ObjectInputStream oos;

    //Byte array to store the message being captured
    ByteArrayInputStream toBeRead;
    //To write the object to the array
    ObjectInputStream oos1;

    @Mock
    ClientModel clientModel;

    @Mock
    ObjectInput objectInput;

    @Mock
    Socket socket;

    @InjectMocks
    InputService inputService;

    @Captor
    private ArgumentCaptor<byte[]> valueCapture;

    InputStream myInputStream;

    UserInfo uInfo;




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
        //objectInputStream = Mockito.mock(objectInputStream.getClass());
        MockitoAnnotations.initMocks(this);
        readValue = new ByteArrayInputStream(new byte[2]);
        toBeRead = new ByteArrayInputStream(new byte[2]);
        /*try {
            oos = new ObjectInputStream(readValue);
            oos1 = new ObjectInputStream(toBeRead);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //inject mocked output stream into InputService class
        try {
            FieldSetter.setField(inputService, inputService.getClass()
                    .getDeclaredField("socket"), socket);
            FieldSetter.setField(inputService, inputService.getClass().
                    getDeclaredField("messageInStream"), objectInput);
            FieldSetter.setField(inputService,inputService.getClass().getDeclaredField("model"),clientModel);
           // FieldSetter.setField(socket,socket.getClass().
           //         getDeclaredField("connected"),new Boolean(true));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("No Such Field");
        }
        //inputService.connectToServer();
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
        //Message message = MessageFactory.createFileMessage(new MessageFile(new File("")),uInfo,"Erik");
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
        //assertEquals(inputService.getMessageIn(),message);
        //Mockito.verify(inputService.disconnect(),atLeastOnce())  ;
        
    }

}
