package com.CEYMChatClient.Services.RemoteServices;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.RemoteServices.IInput;
import com.CEYMChatClient.Services.RemoteServices.IOutput;

public interface IServiceFactory {
    IOutput createOutputService(ClientModel model);

    IInput createInputService(ClientModel model);

}
