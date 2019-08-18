package com.CEYMChatServer;

import com.CEYMChatLib.Message;

public interface IMessageObserver {

    /**
     * to update the observer state according to the coming updated state
     * @param message the new state
     */
    void updateNewMessage(Message message);

}
