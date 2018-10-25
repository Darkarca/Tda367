package com.CEYMChatServer.Services;

import com.CEYMChatLib.IObserveable;

/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader extends IObserveable {

    /**
     * A method to stop the IReader to stop reading.
     */
    void stop();
}
