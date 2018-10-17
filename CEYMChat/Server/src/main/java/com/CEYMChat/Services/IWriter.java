package com.CEYMChat.Services;

import com.CEYMChat.Message;

public interface IWriter {

    void setOutMessage(Message m);
    void writeToStream();
}
