package com.CEYMChatClient.Services.FileServices;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class Configurations implements IConfigurable{

    private static IConfigurable configurations_instance = null;
    private Properties config;
    private String configPath;

    // private constructor restricted to this class itself
    private Configurations() {
        loadProperties();
    }

    // static method to create instance of Configurations class
    public static IConfigurable getInstance() {
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
            configPath = Configurations.class.getClassLoader().getResource("config.properties").getPath();
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

    /**
     * set configurations to the compiled properties file.
     */
    public void setConfigProperty(String key, String value) {
        try {
            config.setProperty(key, value);
            OutputStream output = new FileOutputStream(configPath);
            config.store((output), null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "file config.properties is not found in resources package", "info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * get configuration value by giving the key.
     */
    public String getConfigProperty(String key) {
        return config.getProperty(key);
    }
}
