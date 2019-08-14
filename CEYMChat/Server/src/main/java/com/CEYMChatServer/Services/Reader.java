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
    private int bytesRead = 0;



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
            unregisterAllObservers();
        } catch (IOException e) {
            System.out.println("Could not stop safely, stopping hard");
            System.exit(1);
            e.printStackTrace();
        }
    }
    private void checkConnectionIsLive(){
        if(!socket.isConnected()){
            stop();
            System.out.println("DISCONNECTED FROM SERVER");
        }
    }
    @Override
    public void run() {
        Message inMessage;
        while (running) {
            checkConnectionIsLive();
            try {
                inMessage = (Message) inputStream.readObject();         // Constantly check the inputStream and casts its object to a message
                MessageType msgType = MessageType.valueOf(inMessage.getType().getSimpleName().toUpperCase());
                switch (msgType) {
                    case MESSAGEFILE: {

                        byte [] receivedFileArray  = new byte [((MessageFile)inMessage.getData()).getByteArray().length];
                        InputStream inputStream = socket.getInputStream();
                        FileOutputStream outStream = new FileOutputStream("Server/messages/" + ((MessageFile)inMessage.getData()).getFileName());
                        BufferedOutputStream bufferedOutStream = new BufferedOutputStream(outStream);
                        int[] i = new int[1];
                        while ((bytesRead = inputStream.read(receivedFileArray)) != 1){
                            i[0] = i[0] + bytesRead;
                            bufferedOutStream.write(receivedFileArray,0,bytesRead);
                            if (i[0] == receivedFileArray.length){
                                break;
                            }

                        }
                        bufferedOutStream.flush();
                        outStream.close();
                        for (IObserver observer: observerList) {
                            observer.update(inMessage);
                        }
                        break;
                      }
                    default:
                        for (IObserver observer: observerList) {observer.update(inMessage);}
                }
            }
            catch(EOFException e){ //This exception will occur if a socket has been unexpectedly closed on client-side.
                stop();
                break;
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void register(IObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void unregister(IObserver observer) {observerList.remove(observer);}

    private void unregisterAllObservers(){
        observerList.clear();
    }
}

