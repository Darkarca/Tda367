package cse.chalmers.CEYMChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Session implements Runnable {
    private Socket client1;
    private Socket client2;
    private DataInputStream inputFromClient1;
    private DataOutputStream outputToClient1;
    private DataInputStream inputFromClient2;
    private DataOutputStream outputToClient2;
    private String textFromC1;
    private String textFromC2;

    // Constructor
    public Session(Socket client1, Socket client2){
        this.client1 = client1;
        this.client2 = client2;
    }
    // implement the run method from the Runnable interface
    public void run (){
        try {
            // Create data input and data output streams
            inputFromClient1 = new DataInputStream(client1.getInputStream());
            outputToClient1 = new DataOutputStream(client1.getOutputStream());
            inputFromClient2 = new DataInputStream(client2.getInputStream());
            outputToClient2 = new DataOutputStream(client2.getOutputStream());

            //new Thread(() -> {


            //while(true){
            try {
                //read from client1 and send to client2
                while((textFromC1 = inputFromClient1.readUTF()) != null){


                    //while (!textFromC1.equals("")) {
                    outputToClient2.writeUTF(textFromC1);
                    //textFromC1 = null;
                    outputToClient2.flush();
                }
                //}
                //read from client2 and send to client1
                while((textFromC2 = inputFromClient2.readUTF()) != null) {
                    //while (!textFromC2.equals("")) {
                    outputToClient1.writeUTF(textFromC2);
                    //textFromC2 = null;
                    outputToClient1.flush();
                    //}
                }
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
            // }
            //}).start();

        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }




}
