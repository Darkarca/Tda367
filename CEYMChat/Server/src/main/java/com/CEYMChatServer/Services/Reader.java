package com.CEYMChatServer.Services;

import com.CEYMChatLib.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** Thread that reads user input and send it to the
 * server implementing the IReader interface.
 */
public class Reader implements Runnable, IReader {
    private List<IObserver> observerList = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private boolean running = true;


    public Reader(Socket socket) {
        this.socket = socket;
        {
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("No socket found");
            }
        }
        Thread rThread = new Thread(this);
        rThread.start();
    }

    /**
     * Safely disconnects the socket and stops the Thread
     */
    public void stop(){
        try {
            running = false;
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message inMessage = (Message) inputStream.readObject();         // Constantly check the inputStream and casts its object to a message
                MessageType msgType = MessageType.valueOf(inMessage.getType().getSimpleName().toUpperCase());
                switch (msgType) {
                    case MESSAGEFILE: {
                        byte [] receivedFileArray  = new byte [((MessageFile)inMessage.getData()).getByteArray().length];
                        InputStream inputStream = socket.getInputStream();
                        FileOutputStream outStream = new FileOutputStream("Server/messages/" + ((MessageFile)inMessage.getData()).getFileName());
                        BufferedOutputStream bufferedOutStream = new BufferedOutputStream(outStream);
                        int bytesRead = inputStream.read(receivedFileArray,0,receivedFileArray.length);
                        int current = bytesRead;
                            bytesRead = inputStream.read(receivedFileArray, current, (receivedFileArray.length-current));
                        bufferedOutStream.write(receivedFileArray, 0 , current);
                        bufferedOutStream.flush();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //bufferedOut.flush();
                        for (IObserver observer: observerList) {
                            observer.update(inMessage);
                        }
                        break;
                    }
                    default:
                        for (IObserver observer: observerList) {
                            observer.update(inMessage);

                        }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(IObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void unregister(IObserver observer) {

    }
}

