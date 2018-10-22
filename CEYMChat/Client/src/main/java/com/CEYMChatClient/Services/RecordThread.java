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
    private File directory = new File("Client/Messages/RAudio.wav");

    // determine the audio format
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    private IController controller;

    // The audio data is captured from the microphone
    private TargetDataLine mic;

    // to decide when to stop recording
    private boolean recording;

    private long maxRecordingTime;

    /**
     * Constructor
     * @param c
     * @param mic
     * @param maxRecordingTime
     */
    public RecordThread(IController c, TargetDataLine mic, long maxRecordingTime){
        this. controller = c;
        this.mic = mic;
        this.maxRecordingTime = maxRecordingTime;
    }

    /**
     * This method makes an audio stream from the
     * mic and write it to a specified file directory
     */
    @Override
    public void run() {
        try {
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
     * This method waits for a specified period of
     * time before it calls the method that stops recording
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
