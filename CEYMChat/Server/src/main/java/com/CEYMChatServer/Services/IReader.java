package com.CEYMChatServer.Services;
/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader {

    /**
     * A method to run the IReader to start reading.
     */
    void run();

    /**
     * A method to stop the IReader to stop reading.
     */
    void stop();
}
