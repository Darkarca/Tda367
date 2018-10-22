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

    private long maxRecordingTime;

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
            maxRecordingTime(maxRecordingTime);
            AudioSystem.write(ais, fileType, directory);
        }  catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This method is called to stop recording
     */
    public void stop(){
        mic.stop();
        mic.close();
    }

    /**
     * This method waits for a specified period of time before it calls the method that stops recording
     * @param maxRecordingTime
     */
    public void maxRecordingTime(long maxRecordingTime){
        Thread wait = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(maxRecordingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stop();
            }

        });
        wait.start(); 
    }
}
