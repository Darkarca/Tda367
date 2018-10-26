package com.CEYMChatClient.Services;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class represent the task of playing a previously recorded audio file
 */
public class PlayBackThread implements Runnable {
    private AudioStream audioStream;
    private String fileName;
/*
    /**
     * Constructor
     * @param fileName
     */
    //public PlayBackThread(String fileName){
        //this.fileName = fileName;
    //}

    /**
     * This method takes an inputstream from an audio file and run it via AudioPlayer class
     */
    public  void run()  {
        // Make an inputstream from file
        String directory = "Client/Messages/Audio.wav";

        // Make an audio stream from a source audio file
        try {
            InputStream inputStream = new FileInputStream(directory);
            audioStream = new AudioStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // running file using AudioPlayer class
        AudioPlayer.player.start(audioStream);
    }
}
