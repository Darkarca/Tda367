import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {

    private int numOfSession = 1;
    @Override
     public void start(Stage primaryStage) {
        TextArea textLog = new TextArea();
        Scene scene = new Scene(new ScrollPane(textLog), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                //the server accepts connections from the clients to make sessions
                while(true){
                    // Listen for a connection request from the first client
                    Socket socket1 = serverSocket.accept();
                    //update the log
                    Platform.runLater(() -> {
                        textLog.appendText(new Date() + ": Client 1 joined session " + numOfSession + '\n');
                        textLog.appendText("Client 1's IP address" + socket1.getInetAddress().getHostAddress() + '\n');
                    });
                    // Listen for a connection request from the second client
                    Socket socket2 = serverSocket.accept();

                    //update the log
                    Platform.runLater(() -> {
                        textLog.appendText(new Date() + ": Client 2 joined session " + numOfSession + '\n');
                        textLog.appendText("Client 2's IP address" + socket2.getInetAddress().getHostAddress() + '\n');
                    });
                    Platform.runLater(() -> {
                        textLog.appendText("Session " + numOfSession++ + " is started " + '\n');
                    });

                    //start a thread for two clients
                    Runnable session = new Session(socket1,socket2);
                    Thread thread = new Thread(session);
                    thread.start();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
     }
     class Session implements Runnable {
        private Socket clint1;
        private Socket clint2;
        private DataInputStream inputFromClient1;
        private DataOutputStream outputToClient1;
        private DataInputStream inputFromClient2;
        private DataOutputStream outputToClient2;
        private String textFromC1;
        private String textFromC2;

        // Constructor
        public Session(Socket clint1, Socket clint2){
            this.clint1 = clint1;
            this.clint2 = clint2;
        }
        // implement the run method from the Runnable interface
        public void run (){
            try {
                // Create data input and data output streams
                inputFromClient1 = new DataInputStream(clint1.getInputStream());
                outputToClient1 = new DataOutputStream(clint1.getOutputStream());
                inputFromClient2 = new DataInputStream(clint2.getInputStream());
                outputToClient2 = new DataOutputStream(clint2.getOutputStream());

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
 }