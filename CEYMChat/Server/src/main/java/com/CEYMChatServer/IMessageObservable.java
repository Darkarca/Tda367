package com.CEYMChatServer;

import com.CEYMChatLib.Message;

public interface IMessageObservable {

    /**
     * to notify the observers
     * @param message the changed state
     */
    void notify(Message message);

    /**
     * to register a new observer
     * @param observer the observer to be registered
     */
    void register(IMessageObserver observer);

    /**
     * to unregister an observer
     * @param observer the observer to be unregistered
     */
    void unregister(IMessageObserver observer);
}
