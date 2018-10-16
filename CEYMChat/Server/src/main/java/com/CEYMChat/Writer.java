package com.CEYMChat;


import com.CEYMChat.Services.IWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Writer implements IWriter {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private Message outMessage;
    private List<UserDisplayInfo>  userInfoList = new ArrayList();

    public Writer(Socket socket) {

        this.socket = socket;
        {
            try {
                outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }
    }

    public synchronized void setOutMessage(Message m){
        outMessage = m;
        writeToStream();
    }

    public void writeToStream(){
        try {
            outputStream.writeObject(outMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


