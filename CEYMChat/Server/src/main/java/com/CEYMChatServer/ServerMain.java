package com.CEYMChatServer;

import com.CEYMChatServer.Model.User;
import com.CEYMChatServer.Services.IReader;
import com.CEYMChatServer.Services.SocketHandler;
import com.CEYMChatServer.Model.ServerModel;
import java.util.List;
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
    public static void main(String[] args) {
        System.out.println("Server running");
        ServerMain main = new ServerMain();
        System.out.println("Enter a port number to use. To use default port '9000', hit enter without typing a number");
        String port;
        Scanner scanner = new Scanner(System.in);
        port = scanner.nextLine();
        model = new ServerModel();
        if ("".equals(port)) {
            socketHandler = new SocketHandler(model);
            System.out.println("Default port used");
        } else {
            int portInt = Integer.parseInt(port);
            socketHandler = new SocketHandler(model, portInt);
            System.out.println("Server hosted with port " + port);
        }
        main.startHandler();
        System.out.println("To view commands, type '-commands'");
        System.out.println("To stop the server, type '-quit'");
        System.out.println("To disconnect the last user connected, type '-disconnect'");
        System.out.println("To restart the server and disconnect all users, type '-restart'");
        System.out.println("To see the currently online users, type '-users'");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("-quit")) {
                break;

            }
            if (input.equals("-disconnect")) {
                List<IReader> readers = socketHandler.getReaders();
                List<User> users = model.getUserList();
                users.remove(users.size() - 1);
                readers.get(readers.size() - 1).stop();
            }
            if (input.equals("-commands")) {
                System.out.println("To view commands, type '-commands'");
                System.out.println("To stop the server, type '-quit'");
                System.out.println("To disconnect a user, type '-disconnect'");
                System.out.println("To restart the server and disconnect all users, type '-restart'");
                System.out.println("To see the currently online users, type '-users'");
            }
            if(input.equals("-users")){
                for (User u:model.getUserList()) {
                    System.out.println(u.getUsername());
                }
            }
            if (input.equals("-restart")) {
                System.out.println("Restart the server");
                socketHandler.closeSocket();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                model = new ServerModel();
                socketHandler.stop();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketHandler = new SocketHandler(model);
                socketHandler.start();
            }
        }
        System.exit(0);


    }
}
