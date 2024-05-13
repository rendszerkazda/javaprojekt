package org.example.feleviprojekt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class FelulnezetController {
    @FXML
    Pane pane;
    public void display(ObservableList<Node> children) {
        //Add children to pane, from the list
        pane.getChildren().addAll(children);
    }
}
