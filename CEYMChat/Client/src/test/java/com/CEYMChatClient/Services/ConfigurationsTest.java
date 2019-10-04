package com.CEYMChatClient.Services;

import com.CEYMChatClient.Services.FileServices.Configurations;
import com.CEYMChatClient.Services.FileServices.IConfigurable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConfigurationsTest {

    IConfigurable testConfig = Configurations.getInstance();

    @Test
    public void getInstanceTest(){
        assertEquals(testConfig,Configurations.getInstance());
    }

    @Test
    public void loadPropertiesTest(){
        testConfig.loadProperties();
        assertEquals(testConfig.getConfigProperty("sentTextFile"),"Client/messages/sent.csv");
    }

    @Test
    public void setPropertiesTest(){
        assertNotEquals(testConfig.getConfigProperty("maxRecordingTime"),"1");
        testConfig.setConfigProperty("maxRecordingTime","1");
        assertEquals(testConfig.getConfigProperty("maxRecordingTime"),"1");
        testConfig.setConfigProperty("maxRecordingTime","30000");
    }
}
