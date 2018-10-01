package com.CEYMChat.MyServices;

import com.CEYMChat.MessageFactory;

import java.io.*;
import java.net.Socket;

public class TextSessionHandler implements IService {
    private Socket clint1;
    private Socket clint2;
    private DataInputStream inputFromClient1;
    private DataOutputStream outputToClient2;
    private String message;
    // Constructor
    public TextSessionHandler(Socket clint1, Socket client2) {
        this.clint1 = clint1;
        this.clint2 = client2;
    }

    // implement the run method from the Runnable interface
    public void run (){
        try {
            // Create data input and data output streams
            inputFromClient1 = new DataInputStream(clint1.getInputStream());
            outputToClient2 = new DataOutputStream(clint2.getOutputStream());

            while(true){
                try {
                    //read from client1 and send to client2
                    message = inputFromClient1.readUTF();
                    outputToClient2.writeUTF(message);
                    outputToClient2.flush();
                }
                catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
