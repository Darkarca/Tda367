package com.CEYMChatClient.Services;

import com.CEYMChatClient.Services.FileServices.Configurations;
import com.CEYMChatClient.Services.FileServices.VoiceServices;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.security.krb5.Config;

import javax.sound.sampled.AudioFileFormat;

import java.io.File;

import static org.junit.Assert.*;

public class VoiceServiceTest {
    private static VoiceServices testService;

    @BeforeClass
    public static void Setup(){
        // Create new voiceService to test
        testService = new VoiceServices(Configurations.getInstance(),AudioFileFormat.Type.WAVE);
    }

    /**
     * Tries to record 2s of audio and fails if an Exception is thrown
     * Passes if there is a soundFile created
     * Be wary of the filepath in configurations, not sure if saved correctly
     * Fails if a sound file could not be created, does not verify that any sound actually was recorded
     * @throws InterruptedException
     */
    @Test
    public synchronized void recordTest() throws InterruptedException {
        String path = System.getProperty("user.dir") + Configurations.getInstance().getConfigProperty("soundFile");
        File file;
        file = new File(path);
        file.delete();
        assertFalse(file.exists());
        try{
            testService.recordVoice();
            wait(2000);         //Records 2s of audio
            testService.stopRecording();
            assertTrue(file.exists());
            testService.playBack();
        }catch(Exception e) {
            fail("Should not have thrown exception");
        }

        file.delete();
    }
}
