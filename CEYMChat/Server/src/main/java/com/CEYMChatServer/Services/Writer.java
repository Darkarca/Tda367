package com.CEYMChatServer.Services;

import com.CEYMChatLib.Message;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 *   Class that writes data to an outputStream
 *   connected to a specific client implementing the IWriter interface.
 */
public class Writer implements IWriter {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private Message outMessage;



    public Writer(Socket socket) {  // Connects to the same socket as the client
        this.socket = socket;
        {
            try {
                outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }
    }

    public void setOutMessage(Message outMessage){  // Sets message to be written and writes it to the stream
        this.outMessage = outMessage;
        writeToStream();
    }

    public void writeToStream(){                        // Writes message to the outputStream
        try {
            outputStream.writeObject(outMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Getters and setters **/

    public Socket getSocket(){
        return this.socket;
    }
    public Message getOutMessage() {
        return outMessage;
    }


}


