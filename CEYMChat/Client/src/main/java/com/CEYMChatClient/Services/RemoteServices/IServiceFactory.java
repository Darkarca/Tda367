package com.CEYMChatClient.Services.RemoteServices;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.RemoteServices.IInput;
import com.CEYMChatClient.Services.RemoteServices.IOutput;

import java.io.IOException;

public interface IServiceFactory {
    IOutput createOutputService(ClientModel model) throws IOException;

    IInput createInputService(ClientModel model);

}
