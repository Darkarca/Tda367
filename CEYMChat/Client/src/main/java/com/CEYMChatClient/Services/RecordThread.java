package com.CEYMChatClient.Services;

import com.CEYMChatClient.Controller.IController;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.TargetDataLine;
import java.io.File;

public class RecordThread implements Runnable{

    // Filepath
    File wavFile = new File("Client/Messages/RAudio.wav");

    // determine the audio format
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    IController c;

    // The audio data is captured from the microphone
    TargetDataLine mic;

    // to decide when to stop recording
    boolean stopRecord;









    @Override
    public void run() {

    }
}
