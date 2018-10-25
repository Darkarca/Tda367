package com.CEYMChatServer.Services;

import com.CEYMChatLib.IObserver;

/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader extends IObserver {

    /**
     * A method to stop the IReader to stop reading.
     */
    void stop();
}
