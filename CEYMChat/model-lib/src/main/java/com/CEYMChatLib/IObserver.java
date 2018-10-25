package com.CEYMChatLib;

import com.CEYMChatLib.Message;

public interface IObserver {
    void update(Message message);
    void disconnect();
}
