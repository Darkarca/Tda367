package View;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class View {
    private TextArea textLog;
    private Scene scene;


    public TextArea getTextLog() {
        return textLog;
    }

    public Scene getScene() {
        return scene;
    }

    public void drawStage (Stage primaryStage){
        textLog = new TextArea();
        scene = new Scene(new ScrollPane(textLog), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
