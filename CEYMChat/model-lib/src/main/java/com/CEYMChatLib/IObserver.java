package com.CEYMChatLib;
public interface IObserver {
    void update(Message message);
    void disconnect();
}
