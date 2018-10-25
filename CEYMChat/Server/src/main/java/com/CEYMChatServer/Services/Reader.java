package com.CEYMChatServer.Services;

import com.CEYMChatLib.*;
import com.CEYMChatServer.Model.ServerModel;
import java.io.*;
import java.net.Socket;
/** Thread that reads user input and send it to the
 * server implementing the IReader interface.
 */
public class Reader implements Runnable, IReader {
    private ServerModel model;
    private Socket socket;
    private ObjectInputStream inputStream;
    private boolean running = true;


    public Reader(ServerModel model, Socket socket) {
        this.model = model;
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
                    case COMMAND: {                                     // A message containing a command is sent to the model so that is can be performed, we stop the thread if the command tells us to disconnect
                        model.performCommand((Command) inMessage.getData(), inMessage.getSender());
                        if(((Command)inMessage.getData()).getCommandName() == CommandName.DISCONNECT){
                            this.stop();
                        }
                        break;
                    }
                    case STRING: {                                      // A string message is simply sent to the model and redistributed to the correct client
                        model.displayMessage(inMessage);
                        model.sendMessage(inMessage, inMessage.getReceiver());
                        break;
                    }
                    case ARRAYLIST: {
                        model.friendSync(inMessage);// An arrayList is sent to the model so that the friendlist of a specific user can be updated
                        model.performCommand(new Command(CommandName.REFRESH_FRIENDLIST,inMessage.getSender()),inMessage.getSender());
                        break;
                    }
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
                        model.sendFile("Server/messages/" + ((MessageFile)inMessage.getData()).getFileName(), inMessage);
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

