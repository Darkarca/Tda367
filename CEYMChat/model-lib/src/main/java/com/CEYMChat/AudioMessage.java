package com.CEYMChat;
import javax.sound.sampled.AudioFormat;

/** The Class that Describe The audio format of the file bring sent and recieved */
public class AudioMessage {
    float sRate;
    int sInbits;
    int channels;
    boolean signed;
    boolean bigEndian;


    /** get the sound file format */
    public AudioFormat getAudioFormat() {
        sRate = 16000.0F;
        sInbits = 16;
        channels = 1;
        signed = true;
        bigEndian = false;
        return new AudioFormat(sRate, sInbits, channels, signed, bigEndian);
    }
}
