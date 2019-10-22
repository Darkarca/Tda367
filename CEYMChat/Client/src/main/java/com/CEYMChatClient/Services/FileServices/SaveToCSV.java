package com.CEYMChatClient.Services.FileServices;

import com.CEYMChatClient.Services.FileServices.Configurations;
import com.CEYMChatClient.Services.FileServices.ISaveMessages;
import com.CEYMChatLib.Message;
import javafx.scene.control.Alert;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class SaveToCSV implements ISaveMessages {

    /** Saves all sent and received messages into a file
     * @param list The list of messages to be saved
     * @param filename The location to save the file to
     */
    public void saveArrayListToFile(List<Message<String>> list, String filename, String username) throws IOException {
        FileWriter writer = new FileWriter( filename);
        for(Message message: list) {
            if(message.getSender().getUsername().equals(username)) {
                writer.write("Me: " + "," + message.getData().toString() + ",");
            }
            else{
                writer.write(message.getSender().getUsername() + ": ," + message.getData().toString() + ",");
            }
        }
        writer.close();
    }

    /** Calls saveArrayListToFile to save all Received messages
     * @param filename the location to save the file to
     */
    public void saveReceivedMessages(List<Message<String>> receivedMessages, String filename, String username) {
        try {
            saveArrayListToFile(receivedMessages, filename, username);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File save error");
            alert.setHeaderText("File save error");
            alert.setContentText("Your files could not be saved.");

            alert.showAndWait();
        }
    }

    /** Calls saveArrayListToFile to save all sent messages
     * @param filename the location to save the file to
     */
    public void saveSendMessages(List<Message<String>> sentMessages, String filename, String username) {
        try {
            saveArrayListToFile(sentMessages, filename, username);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File save error");
            alert.setHeaderText("File save error");
            alert.setContentText("Your files could not be saved.");

            alert.showAndWait();
        }
    }

    @Override
    /**
     * Saves messages locally so that they can
     * be loaded the next time you load the client
     */
    public void saveMessages(List<Message<String>> receivedMessages, List<Message<String>> sentMessages, String username) {
        Path path = FileSystems.getDefault().getPath("");
        String absPath;
        System.out.println(path.toAbsolutePath());
        if(path.toAbsolutePath().endsWith("Client")){
            absPath = System.getProperty("user.dir");

        }else{
            absPath = System.getProperty("user.dir") + "/Client";
        }
        saveReceivedMessages(receivedMessages,absPath + "/messages/received.csv", username);
        saveSendMessages(sentMessages,absPath + "/messages/sent.csv", username);
    }
}
