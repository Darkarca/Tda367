package com.CEYMChatServer.Services;
/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader {

    /**
     * A method to stop the IReader to stop reading.
     */
    void stop();
}
