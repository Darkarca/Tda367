package com.CEYMChatLib;

import com.CEYMChatLib.Message;

public interface IObservable {
    void update(Message message);
    void disconnect();
}
