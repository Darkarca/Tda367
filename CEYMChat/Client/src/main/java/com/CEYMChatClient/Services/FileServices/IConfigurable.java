package com.CEYMChatClient.Services.FileServices;

public interface IConfigurable {

    /**
     * set configuration key-value to the a properties file.
     */
    void setConfigProperty(String key, String value);

    /**
     * get configuration value by giving the key.
     */
    String getConfigProperty(String key);

}
