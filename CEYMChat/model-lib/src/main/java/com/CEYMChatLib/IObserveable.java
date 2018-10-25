package com.CEYMChatLib;

public interface IObserveable {
    void register(IObserver observer);
    void unregister(IObserver observer);
}
