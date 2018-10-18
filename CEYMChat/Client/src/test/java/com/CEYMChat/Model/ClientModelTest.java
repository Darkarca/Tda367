package com.CEYMChat.Model;

import com.CEYMChat.ClientController;
import org.junit.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientModelTest {

    ServerSocket serverSocket;
    OutputStream outputStream;
    InputStream inputStream;
    Socket socket;
    ClientModel clientModel;

    public void setUp() throws IOException {
        {
            try {
                serverSocket = new ServerSocket(9000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void inIt() throws IOException {
        setUp();
        clientModel = new ClientModel();
    }

/*    @Test
    public void connectToServer() throws IOException, ClassNotFoundException {
        inIt();
        ClientController clientController = new ClientController();
        clientModel.connectToServer(clientController);
                try {
                    socket = serverSocket.accept();
                    System.out.println("Connected socket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        // Test send-receive
        int sent = 2;
        outputStream = socket.getOutputStream();
        outputStream.write(sent);
        inputStream = clientModel.getSocket().getInputStream();
        int received = inputStream.read();
        Assert.assertEquals(sent, received);
    }

    @Test
    public void createFriendList() {
    }

*/
}
