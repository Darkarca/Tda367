package com.CEYMChatLib;

public interface IObserver {
    void register(IObservable observer);
    void unregister(IObservable observer);
}
