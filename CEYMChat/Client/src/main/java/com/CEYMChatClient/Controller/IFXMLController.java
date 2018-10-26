package com.CEYMChatClient.Controller;

import javafx.scene.layout.AnchorPane;

public interface IFXMLController {

    /**
     * Loads the .fmxl file.
     */
    void load();

    /**
     * @return Returns the parent-pane for the .fxml file.
     */
    AnchorPane getPane();
}
