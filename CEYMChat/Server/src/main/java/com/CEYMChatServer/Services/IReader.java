package com.CEYMChatServer.Services;
/**
 * Interface for classes intending to read messages from an inputStream
 */

public interface IReader {
    void run();
    void stop();
}
