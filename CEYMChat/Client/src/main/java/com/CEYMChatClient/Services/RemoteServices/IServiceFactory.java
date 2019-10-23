package com.CEYMChatClient.Services.RemoteServices;

import com.CEYMChatClient.Model.ClientModel;

import java.io.IOException;

public interface IServiceFactory {
    IOutput createOutputService(ClientModel model) throws IOException;

    IInput createInputService(ClientModel model);

}
