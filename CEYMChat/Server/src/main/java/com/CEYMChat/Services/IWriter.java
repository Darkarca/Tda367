package com.CEYMChat.Services;

import com.CEYMChat.Message;
import com.CEYMChat.User;

import java.util.List;

public interface IWriter {

    void setOutMessage(Message m);
    void sendUserList(List<User> user);
}
