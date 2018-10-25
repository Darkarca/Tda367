package com.CEYMChatClient.Model;

import com.CEYMChatLib.Message;

public interface IObservable {
    void update(Message message);
    void disconnect();
}
