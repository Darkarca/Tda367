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

    /**
     * Tests that the disconnect method correctly disconnects the socket
     * Fails if the socket cannot be properly disconnected (unrealistic to happen since the socket is mocked in this instance)
     */
    @Test
    public void testDisconnect(){
        inputService.disconnect();
        assertEquals(socket.isConnected(), false);
        inputService.checkConnectionIsLive();
        Mockito.verify(socket,atLeast(2)).isConnected();
    }

    /**
     * Tests that the inputService can correctly read and handle a String Message
     * Fails if the data in the expected message is not the same as the data in the actually read message
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    @Test
    public synchronized void testReadString() throws IOException, ClassNotFoundException, InterruptedException {
        // Create a fake message to send
        Message message = MessageFactory.createStringMessage("hi", uInfo, "Erik");
        // Mock behavious for objectInput
        Mockito.when(objectInput.readObject()).thenReturn(message);
        // Read the message and handle it
        inputService.read();
        // Wait to make sure the inputService thread has time to handle the message before we evaluate it
        wait(100);
        // Disconnect (kill) the thread
        inputService.disconnect();
        // Make sure the read message is the same as the expected message
        assertEquals(inputService.getMessageIn().getData().toString(),message.getData().toString());
        // Verify that the mocked behaviour was 'accessed'
        Mockito.verify(objectInput,atLeast(1)).readObject();
    }

    /**
     * Tests that the inputService can correctly read and handle an ArrayList Message
     * Fails if the data in the expected message is not the same as the data in the actually read message
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    @Test
    public synchronized void testReadList() throws IOException, ClassNotFoundException, InterruptedException {
        // Create a fake message to send
        Message message = new Message(new ArrayList<>(),uInfo);
        // Mock behavious for objectInput
        Mockito.when(objectInput.readObject()).thenReturn(message);
        // Read the message and handle it
        inputService.read();
        // Wait to make sure the inputService thread has time to handle the message before we evaluate it
        wait(100);
        // Disconnect (kill) the thread
        inputService.disconnect();
        // Make sure the read message is the same as the expected message
        assertEquals(inputService.getMessageIn().getData().getClass(),message.getData().getClass());
        // Verify that the mocked behaviour was 'accessed'
        Mockito.verify(objectInput,atLeast(1)).readObject();

    }

    /**
     * Tests that the inputService can correctly read and handle a File Message
     * Fails if the data in the expected message is not the same as the data in the actually read message
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    @Test
    public synchronized void testReadFile() throws IOException, ClassNotFoundException, InterruptedException {
        // Create a fake message to send
        MessageFile file = new MessageFile(new File("sent.csv"));
        Message message = MessageFactory.createFileMessage(file,uInfo,"Erik");
        // Mock behavious for objectInput
        Mockito.when(objectInput.readObject()).thenReturn(message);
        // Read the message and handle it
        inputService.read();
        // Wait to make sure the inputService thread has time to handle the message before we evaluate it
        wait(100);
        // Disconnect (kill) the thread
        inputService.disconnect();
        // Make sure the read message is the same as the expected message
        assertEquals(inputService.getMessageIn().getReceiver(),message.getReceiver());
        // Verify that the mocked behaviour was 'accessed'
        Mockito.verify(objectInput,atLeast(1)).readObject();

    }

    /**
     * Tests that the inputService can correctly read and handle a Command Message
     * Fails if the data in the expected message is not the same as the data in the actually read message
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    @Test
    public synchronized void testReadCommand() throws IOException, ClassNotFoundException, InterruptedException {
        // Create a fake message to send
        Message message = MessageFactory.createCommandMessage(new Command(CommandName.DISCONNECT,"disconnect"),uInfo);
        // Mock behavious for objectInput
        Mockito.when(objectInput.readObject()).thenReturn(message);
        // Read the message and handle it
        // Message should disconnect the service, killing the thread afterwards
        inputService.read();
        // Wait to make sure the inputService thread has time to handle the message before we evaluate it
        wait(100);
        // Make sure the read message is the same as the expected message
        assertEquals(((Command)(inputService.getMessageIn().getData())).getCommandName(),((Command)message.getData()).getCommandName());
        // Make sure the read command disconnected the service correctly
        assertEquals(socket.isConnected(), false);
        // Verify that the mocked behaviour was 'accessed'
        Mockito.verify(objectInput,atLeast(1)).readObject();

    }

}
