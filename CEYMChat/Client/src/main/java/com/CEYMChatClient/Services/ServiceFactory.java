package com.CEYMChatClient.Services;

import com.CEYMChatClient.Model.ClientModel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class ServiceFactory implements IServiceFactory{

    private Socket socket;

    public ServiceFactory() {
        try {
            socket = new Socket("localhost", 9000);
        } catch (Exception ex){
            System.out.println("Fatal Error: The Server is not started!");
            System.exit(0);
        }
    }

    public ServiceFactory(String hostname) {
        try {
            socket = new Socket(hostname, 9000);
        } catch (IOException e) {
            System.out.println("Could not initialize the serverSocket, exiting...");
            System.exit(1);
            e.printStackTrace();
        }
    }

    public IInput createInputService(ClientModel model){
        return new InputService(model, socket);

    }
    public IOutput createOutputService(ClientModel model){
        return new OutputService(model, socket);
    }

}
