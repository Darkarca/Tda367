package com.CEYMChatLib;
public interface IMessageObserver {
    void updateNewMessage(Message message);
    void disconnect();
}
