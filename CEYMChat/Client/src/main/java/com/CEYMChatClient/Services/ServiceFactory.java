package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.IController;
import com.CEYMChatClient.Model.ClientModel;

public class ServiceFactory implements IServiceFactory{

    public IInput createInputService(ClientModel model, IController controller){
        return new InputService(model, controller);
    }
    public IOutput createOutputService(ClientModel model){
        return new OutputService(model);
    }

}
