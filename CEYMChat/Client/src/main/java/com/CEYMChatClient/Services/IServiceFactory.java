package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.IClientController;
import com.CEYMChatClient.Model.ClientModel;

public interface IServiceFactory {
    IOutput createOutputService(ClientModel model);

    IInput createInputService(ClientModel model, IClientController clientController);
}
