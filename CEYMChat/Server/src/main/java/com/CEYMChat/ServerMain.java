package com.CEYMChat;

import com.CEYMChat.Command;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain extends Application{

    ServerSocket server;
    List<Connection> clientList = new ArrayList<Connection>();
    ObjectInputStream messageInStream;
    ObjectOutputStream messageOutStream;



    public void start(Stage primaryStage){
        try{
            server  = new ServerSocket(8989);
            System.out.println("Server running");
            startConnection();
        while(true){
            for (Connection c:clientList) {
                displayMessage(getMessage(c));
            }


        }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void displayMessage(Message m){
        System.out.println(m.getData());
    }

    public void logInUser(Command c) {
        if (checkUser(c.getCommandData())) {
            clientList.add(new Connection());
        }
    }

    public void registerUser(){

    }

    public void performCommand(Command c){
    }

    public Message getMessage(Connection c){
        try {
         return new Message(messageInStream.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startConnection() throws IOException {
        System.out.print("Method called");
        Connection c = new Connection();
        c.start();
        while(clientList.isEmpty()) {
            c.socket = server.accept();
            clientList.add(c);
            System.out.println("Client connected");
        }
        System.out.println("Method ended");
    }

    public void startSession(){

    }

    private boolean checkUser(String details){

        return true;
    }

}
