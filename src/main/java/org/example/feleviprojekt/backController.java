package org.example.feleviprojekt;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class backController extends Controller{
    @FXML
    AnchorPane viewPane;
    AnchorPane configPane;
    public void display(ObservableList<Node> children) {
        //Add children nodes to the anchorpane
        viewPane.getChildren().addAll(children);
        //TODO: Correct center nodes position
        //TODO: Config pane children copy
    }
    public void fullView(ActionEvent event) throws IOException {
        //Go back to full view
        super.fullView(event);
    }
    public void topView(ActionEvent event) throws IOException {
        //Go to top view
        fullView(event);
        super.topView(event);
    }
    public void sideView(ActionEvent event) throws IOException {
        //Go to side view
        super.sideView(event);
    }

}
