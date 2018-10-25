package com.CEYMChatLib;

import java.io.File;
import java.io.Serializable;

public class MessageFile implements Serializable {
    private File file;
    private byte[] byteArray;
    private String fileName;

    public MessageFile(File file2){
        this.file = file2;
        this.byteArray = new byte[(int)file2.length()];
        this.fileName = file2.getName();
    }

    public File getFile() {
        return file;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public String getFileName() {
        return fileName;
    }
}
