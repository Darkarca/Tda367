package com.CEYMChatServer.Services;

import com.CEYMChatLib.IMessageObserveable;

/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader extends IMessageObserveable {

    /**
     * A method to stop the IReader to stop reading.
     */
    void stop();
}
