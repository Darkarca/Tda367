package com.CEYMChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadThread implements Runnable{
    ServerModel model;
    Socket socket;
    ObjectInputStream inputStream;


    public ReadThread(ServerModel model, Socket socket){
        this.model = model;
        this.socket = socket;
        {
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
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
                model.displayMessage((Message)inputStream.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }




}
