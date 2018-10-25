package com.CEYMChatClient.Model;

public interface IObserver {
    void register(IObservable observer);
    void unregister(IObservable observer);
}
