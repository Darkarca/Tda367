package com.CEYMChatServer;

import com.CEYMChatServer.Services.SocketHandler;
import com.CEYMChatServer.Model.ServerModel;
import java.util.Scanner;

/**
 * Class that starts the server
 */
public class ServerMain{
    static private ServerModel model;
    static private SocketHandler socketHandler;
    private void startHandler(){
        socketHandler.start();
    }
    public static void main(String[] args){
        System.out.println("Server running");
        ServerMain main = new ServerMain();
        System.out.println("Enter a port number to use. To use default port '9000', hit enter without typing a number");
        String port;
        Scanner scanner = new Scanner(System.in);
        port = scanner.nextLine();
        if("".equals(port)){
            model = new ServerModel();
            System.out.println("Default port used");
        }
        else {
            int portInt = Integer.parseInt(port);
            model = new ServerModel(portInt);
            System.out.println("Server hosted with port " + port);
        }
        socketHandler = new SocketHandler(model);
        main.startHandler();
        System.out.println("To stop the server, type 'quit'");
        while(true) {
            if (scanner.nextLine().equals("quit")) {
                break;

            }
        }
        System.exit(0);

    }
}
