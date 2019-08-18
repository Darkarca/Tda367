package com.CEYMChatLib;

public interface IMessageObserveable {
    void register(IMessageObserver observer);
    void unregister(IMessageObserver observer);
}
