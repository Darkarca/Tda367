package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.IClientController;
import com.CEYMChatClient.Model.ClientModel;

import java.io.IOException;
import java.net.Socket;

public class ServiceFactory implements IServiceFactory{

    private Socket socket;

    public ServiceFactory() throws IOException {
        socket = new Socket("localhost", 9000);
    }

    public ServiceFactory(String hostname) {
        try {
            socket = new Socket(hostname, 9000);
        } catch (IOException e) {
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
