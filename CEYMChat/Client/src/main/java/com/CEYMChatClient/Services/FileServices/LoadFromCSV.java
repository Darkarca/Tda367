package com.CEYMChatClient.Services.FileServices;

import com.CEYMChatClient.Services.FileServices.ILoadMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadFromCSV implements ILoadMessages {
    @Override
    /** Loads messages that were saved during the last session
     * @param filename the location to load messages from
     */
    public List<String> loadSavedMessages(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = "";
        String cvsSplitBy = ",";
        String [] savedMessages = {};
        while((line = reader.readLine())!=null){
            savedMessages = line.split(cvsSplitBy);
        }
        return new ArrayList<String>(Arrays.asList(savedMessages));
    }
}
