package com.CEYMChatClient.Services;

import com.CEYMChatClient.Services.FileServices.Configurations;
import com.CEYMChatClient.Services.FileServices.IConfigurable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConfigurationsTest {

    IConfigurable testConfig = Configurations.getInstance();

    /**
     * Tests that the getInstance does not create a secondInstance if one is already created
     * Fails if there are two different instances of the configurations
     * Fails if either configurations are null
     */
    @Test
    public void getInstanceTest(){
        assertEquals(testConfig,Configurations.getInstance());
    }

    /**
     * Tests that the loadProperties loads a correct property, in this case the sentTextFile property
     * Fails if the loaded property is different from the expected, check if the saved property has been changed
     */
    @Test
    public void loadPropertiesTest(){
        testConfig.loadProperties();
        assertEquals(testConfig.getConfigProperty("sentTextFile"),"Client/messages/sent.csv");
    }

    /**
     * Tests that the properties can be set from the configurations class,
     * sets the maxRecordingTime to its default value afterwards at 30000
     * Fails if the property cannot be set from the configurations class
     * Fails if the loadPropertyTest fails
     */
    @Test
    public void setPropertiesTest(){
        assertNotEquals(testConfig.getConfigProperty("maxRecordingTime"),"1");
        testConfig.setConfigProperty("maxRecordingTime","1");
        assertEquals(testConfig.getConfigProperty("maxRecordingTime"),"1");
        testConfig.setConfigProperty("maxRecordingTime","30000");
    }
}
