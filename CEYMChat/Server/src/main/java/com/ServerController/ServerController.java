package ServerController;

import Model.Server;
import Service.*;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;


public class ServerController extends Application {
    private Server server = Server.getServerInstans(this);
    private View view = new View();

    public View getView() {
        return view;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        view.drawStage(primaryStage);
        server.setUpServer();

        Runnable clientH1 = new ClientHandlar(server.getSocket1(),server.getSocket2());
        Thread thread1 = new Thread(clientH1);
        thread1.start();

        Runnable clientH2 = new ClientHandlar(server.getSocket2(),server.getSocket1());
        Thread thread2 = new Thread(clientH2);
        thread2.start();
    }
}
