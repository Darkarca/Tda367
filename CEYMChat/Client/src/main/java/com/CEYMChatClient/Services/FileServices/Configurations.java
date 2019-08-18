package com.CEYMChatClient.Services.FileServices;

import com.CEYMChatClient.Services.SaveToCSV;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {

    public static Properties config;

    /**
     * Loads configurations from the properties file.
     */
    public static void loadProperties() {
        InputStream input = (SaveToCSV.class.getClassLoader().getResourceAsStream("config.properties"));
        config = new Properties();
        try {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error, File Not Found");
        }
    }

}
