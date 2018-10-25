package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.IController;
import com.CEYMChatClient.Model.ClientModel;

import java.io.IOException;
import java.net.Socket;

public class ServiceFactory implements IServiceFactory{

    private Socket socket = new Socket("localhost", 9000);

    public ServiceFactory() throws IOException {
    }

    public IInput createInputService(ClientModel model){
        return new InputService(model, socket);
    }
    public IOutput createOutputService(ClientModel model){
        return new OutputService(model, socket);
    }

}
