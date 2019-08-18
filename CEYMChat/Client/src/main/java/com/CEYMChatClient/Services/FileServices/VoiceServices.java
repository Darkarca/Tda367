package com.CEYMChatClient.Services.FileServices;

import javafx.scene.control.Alert;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VoiceServices implements IVoice {

    /**
     * An audio stream to be read from an input stream.
     */
    private AudioStream audioStream;

    /**
     * Holds the program configurations.
     */
    private IConfigurable config;

    /**
     * Holds the directory path of the recorded file.
     */
    private String directory;

    /**
     * determine the audio File format.
     */
    private AudioFileFormat.Type fileFormat;

    /**
     * The audio data is captured from the microphone.
     */
    private TargetDataLine mic;

    /**
     * Holds The maximum recording time.
     */
    private long maxRecordingTime;


    /**
     * Constructs A voice Service with the appropriate configurations.
     * @param config Configurations loaded from the properties file.
     */
    public VoiceServices(IConfigurable config, AudioFileFormat.Type type) {
        this.config = config;
        maxRecordingTime = Integer.parseInt(config.getConfigProperty("maxRecordingTime"));
        directory = config.getConfigProperty("soundFile");
        fileFormat = type;
    }

    /**
     * Makes an audio stream from the
     * mic and write it to the specified file directory
     */
    @Override
    public void recordVoice() {

        try {
            AudioFormat aF = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, aF);
            mic = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            mic.open(aF);
            mic.start();

            new Thread(()-> {
                try {
                    AudioInputStream ais = new AudioInputStream(mic);

                    // start recording
                    System.out.println("Recording...");
                    stopRecordingAfter(maxRecordingTime);
                    AudioSystem.write(ais, fileFormat, new File(directory));
                }  catch (IOException ioe) {
                    System.out.println("AudioStream not ran correctly");
                    ioe.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            StackTraceElement stackEle[] = e.getStackTrace();
            for (StackTraceElement val : stackEle) {
                System.out.println(val);
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Recording error");
            alert.setHeaderText("Recording error!");
            alert.setContentText("Your recording failed! Try again.");

            alert.showAndWait();
        }



    }

    /**
     * Stops the recording of the voice.
     * @throws IOException
     */
    @Override
    public void stopRecording() {
        mic.stop();
        mic.close();
    }

    /**
     * Takes an input stream from an audio file and run it via AudioPlayer class
     */
    @Override
    public void playBack() {
        new Thread(()-> {
            // Make an audio stream from a source audio file
            try {

                InputStream inputStream = new FileInputStream(directory);
                audioStream = new AudioStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // running file using AudioPlayer class
            AudioPlayer.player.start(audioStream);
        }).start();
    }

    /**
     * This method waits for a specified period of
     * time before it calls the method that stops recording
     * @param maxRecordingTime
     */
    private void stopRecordingAfter(long maxRecordingTime){
        Thread wait = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(maxRecordingTime);
                } catch (InterruptedException e) {
                    System.out.println("Sleep interrupted");
                    e.printStackTrace();
                }
                stopRecording();
            }

        });
        wait.start();
    }

    /**
     * Retrieves audio format configurations from the config file.
     * @return AudioFormat object.
     */
    private AudioFormat getAudioFormat() {
        float sRate = Float.parseFloat(config.getConfigProperty("sampleRate"));
        int sInbits = Integer.parseInt(config.getConfigProperty("sampleSizeInBits"));
        int channels = Integer.parseInt(config.getConfigProperty("channels"));
        boolean signed = Boolean.parseBoolean(config.getConfigProperty("signed"));
        boolean bigEndian = Boolean.parseBoolean(config.getConfigProperty("bigEndian"));

        return new AudioFormat(sRate, sInbits, channels, signed, bigEndian);
    }
}
