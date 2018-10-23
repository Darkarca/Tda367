package com.CEYMChatLib;
import javax.sound.sampled.AudioFormat;

/** The Class that Describe The audio format of the file bring sent and recieved */
public class AudioMessage {

    /** get the sound file format */
    public AudioFormat getAudioFormat() {
        float sRate = 16000.0F;
        int sInbits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sRate, sInbits, channels, signed, bigEndian);
    }
}
