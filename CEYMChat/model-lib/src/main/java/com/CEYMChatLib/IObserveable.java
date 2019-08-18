package com.CEYMChatLib;

public interface IObserveable {
    void notify(Message message);
    void register(IObserver observer);
    void unregister(IObserver observer);
}
