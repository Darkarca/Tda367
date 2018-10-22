package com.CEYMChatClient.Services;
import com.CEYMChatClient.Controller.IController;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.File;
import java.io.IOException;

/**
 * This class implements the Runnable to record audio while chatting
 */
public class RecordThread implements Runnable{

    // Filepath
    File directory = new File("Client/Messages/RAudio.wav");

    // determine the audio format
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    IController c;

    // The audio data is captured from the microphone
    TargetDataLine mic;

    // to decide when to stop recording
    boolean stopRecord;

    /**
     * This method makes an audio stream from the mic and write it to a specified file directory
     */
    @Override
    public void run() {
        try {
            System.out.println("Capturing Audio...");
            AudioInputStream ais = new AudioInputStream(mic);

            // start recording
            System.out.println("Recording...");
            AudioSystem.write(ais, fileType, directory);
        }  catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
