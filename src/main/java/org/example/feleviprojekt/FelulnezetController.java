package org.example.feleviprojekt;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FelulnezetController {
    @FXML
    AnchorPane NezetPane;

    AnchorPane konfigPane;
    public void display(ObservableList<Node> children) {
        //Add children to pane, from the list
        NezetPane.getChildren().addAll(children);
    }
    public void fullView(ActionEvent event) throws IOException {
        Controller controller = new Controller();
        controller.fullView(event);
    }
}
