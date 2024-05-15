package org.example.feleviprojekt;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class topController {
    @FXML
    AnchorPane viewPane;
    AnchorPane configPane;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void display(ObservableList<Node> children) {
        //Add children nodes to the anchorpane
        viewPane.getChildren().addAll(children);
        //TODO: Correct center nodes position
        //TODO: Config pane children copy
    }

    public void backView(ActionEvent event) throws IOException {
        //Go to backview
        Parent root = FXMLLoader.load(getClass().getResource("/backView.fxml"));
        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void sideView(ActionEvent event) throws IOException {
        //Go to side view
        Parent root = FXMLLoader.load(getClass().getResource("/sideView.fxml"));
        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
