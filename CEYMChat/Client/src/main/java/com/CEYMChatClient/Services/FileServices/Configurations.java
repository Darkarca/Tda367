package com.CEYMChatClient.Services.FileServices;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class Configurations {

    private static Configurations configurations_instance = null;
    private Properties config;

    // private constructor restricted to this class itself
    private Configurations() {
        loadProperties();
    }

    // static method to create instance of Configurations class
    public static Configurations getInstance() {
        if (configurations_instance == null)
            configurations_instance = new Configurations();

        return configurations_instance;
    }


    /**
     * Loads configurations from the properties file.
     */
    public void loadProperties() {
        InputStream input = null;
        try {
            input = Configurations.class.getClassLoader().getResourceAsStream("config.properties");
            config = new Properties();
            config.load(input);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "file config.properties is not found in resources package", "info", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "file config.properties is not found in resources package", "info", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public Properties getConfig() {
        return config;
    }
}
