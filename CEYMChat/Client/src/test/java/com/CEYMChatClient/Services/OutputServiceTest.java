package com.CEYMChatClient.Services;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.RemoteServices.OutputService;
import com.CEYMChatLib.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;

public class OutputServiceTest {

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
    ObjectOutput objectOutputStream;

    @Mock
    Socket socket;

    @InjectMocks
    OutputService outputService;

    @Captor
    ArgumentCaptor<Message> argumentCaptor;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        toBeSent = new ByteArrayOutputStream();
        toBeWritten = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(toBeSent);
            oos1 = new ObjectOutputStream(toBeWritten);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //inject th mocked output stream in the outputService class
        try {
            FieldSetter.setField(outputService, outputService.getClass().
                    getDeclaredField("messageOutStream"), objectOutputStream);
            FieldSetter.setField(outputService, outputService.getClass()
                    .getDeclaredField("socket"), socket);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("No Such Field");
        }

    }

    //creates a text message, writs it to a byte array, tests the send message
    // method, captures the argument being written to the output stream, serialize the
    // captured argument and finally checks is the sent message is equal to the captured argument.
    @Test
    public void sendTextMessage() throws IOException {
        UserInfo mhd = new UserInfo();
        Message<String> textMessage = new Message<>("Hello World", mhd);
        oos.writeObject(textMessage);
        oos.flush();

        //testing
        outputService.sendMessage(textMessage);
        //verifying
        verify(objectOutputStream).writeObject(argumentCaptor.capture());

        Message<String> message = argumentCaptor.getValue();
        oos1.writeObject(message);
        oos.flush();

        assertArrayEquals(toBeSent.toByteArray(), toBeWritten.toByteArray());

    }

    //creates a command message, writs it to a byte array, tests the send message
    // method, captures the argument being written to the output stream, serialize the
    // captured argument and finally checks is the sent message is equal to the captured argument.
    @Test
    public void sendMessage() throws IOException {
        Command command = new Command(CommandName.SET_USER, "Yazan");
        Message<Command> cmdMessage = new Message<>(command);
        oos.writeObject(cmdMessage);
        oos.flush();

        //testing
        outputService.sendMessage(cmdMessage);
        //verifying
        verify(objectOutputStream).writeObject(argumentCaptor.capture());

        Message<Command> message = argumentCaptor.getValue();
        oos1.writeObject(message);
        oos1.flush();

        assertArrayEquals(toBeSent.toByteArray(), toBeWritten.toByteArray());
    }

    @Test
    public void updateNewMessage() throws IOException {
        when(clientModel.getUsername()).thenReturn("mhd");
        UserInfo mhd = new UserInfo();
        mhd.setUsername(clientModel.getUsername());

        Message<String> textMessage = new Message<>("Hello World", mhd);
        oos.writeObject(textMessage);
        oos.flush();

        outputService.updateNewMessage(textMessage);

        verify(objectOutputStream).writeObject(argumentCaptor.capture());

        Message<Command> message = argumentCaptor.getValue();
        oos1.writeObject(message);
        oos.flush();

        assertArrayEquals(toBeSent.toByteArray(), toBeWritten.toByteArray());
    }

    @Test
    public void testDisconnect(){
        outputService.disconnect();
        assertEquals(socket.isConnected(), false);
        Mockito.verify(socket,atLeastOnce()).isConnected();
    }

    @Test
    public void testConnect(){

    }
  /*  @Test
    public void disconnect() {
    outputService.disconnect();
    assertEquals(oos.toString(),oos1.toString());

    }*/
}