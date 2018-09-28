package Model;
import java.io.*;
import java.net.*;
import java.util.Date;

import ServerController.ServerController;
import Service.IService;
import View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server{
    private Socket socket1;
    private Socket socket2;
    private int numOfSession = 1;


    private ServerController serverController;
    private View view;
    private static Server server;


    private Server(ServerController serverController){
        this.serverController = serverController;
        //view = serverController.getView();
        setUpServer();
    }

    public static Server getServerInstans(ServerController serverController){
        if (server == null)
            return server = new Server(serverController);
        else return server;
    }

    public Socket getSocket1() {
        return socket1;
    }

    public Socket getSocket2() {
        return socket2;
    }


    public void setUpServer() {
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                //the server accepts connections from the clients to make sessions
                while (true) {
                    // Listen for a connection request from the first client
                    socket1 = serverSocket.accept();
                    //update the log
                    Platform.runLater(() -> {
                        server.serverController.getView().getTextLog().appendText(new Date() + ": Client 1 joined session number " + numOfSession + '\n');
                        server.serverController.getView().getTextLog().appendText("Client 1's IP address" + socket1.getInetAddress().getHostAddress() + '\n');
                    });
                    // Listen for a connection request from the second client
                    socket2 = serverSocket.accept();

                    //update the log
                    Platform.runLater(() -> {
                        server.serverController.getView().getTextLog().appendText(new Date() + ": Client 2 joined session number " + numOfSession + '\n');
                        server.serverController.getView().getTextLog().appendText("Client 2's IP address" + socket2.getInetAddress().getHostAddress() + '\n');
                    });
                    Platform.runLater(() -> {
                        server.serverController.getView().getTextLog().appendText("Session " + numOfSession++ + " is started " + '\n');
                    });
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
 }