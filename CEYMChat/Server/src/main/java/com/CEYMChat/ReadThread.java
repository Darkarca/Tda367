package com.CEYMChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThread implements Runnable{
    ServerModel model;
    Socket socket;
    DataInputStream inputStream;


    public ReadThread(ServerModel model, Socket socket){
        this.model = model;
        this.socket = socket;
        {
            try {
                inputStream = new DataInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }


    }



    @Override
    public void run() {
        while(true){
            try {
                model.displayMessage(inputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




}
