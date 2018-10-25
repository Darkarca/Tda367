package com.CEYMChatClient;

import com.CEYMChatClient.Controller.ClientController;
import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatClient.Services.IInput;
import com.CEYMChatClient.Services.IOutput;
import com.CEYMChatClient.Services.IServiceFactory;
import com.CEYMChatClient.Services.ServiceFactory;
import com.CEYMChatLib.Command;
import com.CEYMChatLib.CommandName;
import com.CEYMChatLib.MessageFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/** This class should do nothing else than to launch the client application. */
public class ClientMain extends Application {

    private IServiceFactory serviceFactory;
    private ClientModel model = new ClientModel();

    {
        try {
            serviceFactory = new ServiceFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Runs the Client module as a main method */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Class.class.getClass().getResource("/View/ClientView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("CEYMChat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ((ClientController)fxmlLoader.getController()).setModel(model);
        IOutput outService = serviceFactory.createOutputService(model);
        IInput inService = serviceFactory.createInputService(model);
        outService.connectToServer();
        inService.connectToServer();
        primaryStage.getScene().getWindow().setOnCloseRequest(Event -> {    // Makes sure the client sends a notification to the Server that it has disconnected if the client is terminated
            try {
                model.saveMessages();
                outService.sendMessage(MessageFactory.createCommandMessage(new Command(CommandName.DISCONNECT, model.getUsername()), model.getUsername()));
                outService.stop();
                inService.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}