package com.CEYMChatServer.Services;

import com.CEYMChatServer.IMessageObservable;

/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader extends IMessageObservable {

    /**
     * A method to stop the IReader to stop reading.
     */
    void stop();
}
