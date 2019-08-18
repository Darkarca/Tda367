package com.CEYMChatClient.Services.RemoteServices;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.FileServices.Configurations;

import java.io.IOException;
import java.net.Socket;

public class ServiceFactory implements IServiceFactory {

    private Socket socket;

    public ServiceFactory() {
        try {
            System.out.println(Configurations.getInstance().getConfig().getProperty("serverPath"));
            socket = new Socket(Configurations.getInstance().getConfig().getProperty("serverPath"), 9000);
        } catch (Exception ex){
            System.out.println("Fatal Error: The Server is not started!");
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public ServiceFactory(String hostname) {
        try {
            socket = new Socket(hostname, 9000);
        } catch (IOException e) {
            System.out.println("Could not initialize the serverSocket, exiting...");
            System.exit(1);
        }
    }

    public IInput createInputService(ClientModel model){
        return new InputService(model, socket);

    }
    public IOutput createOutputService(ClientModel model){
        return new OutputService(model, socket);
    }

}
