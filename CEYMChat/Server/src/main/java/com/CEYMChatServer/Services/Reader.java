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
                    case MESSAGEFILE: {                                        // A a message containing a FILE is received and redistributed, the FILE will be corrupt so it needs to be received via a separate inputStream.
                        InputStream inputStream = socket.getInputStream();
                        FileOutputStream fileOut = new FileOutputStream("Server/messages/" + ((MessageFile)inMessage.getData()).getFileName());
                        BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
                        int bytesRead = inputStream.read(((MessageFile)(inMessage.getData())).getByteArray(),0,(((MessageFile)(inMessage.getData())).getByteArray().length));
                        int current = bytesRead;
                            bufferedOut.write(((MessageFile)inMessage.getData()).getByteArray(), 0 , current);
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

