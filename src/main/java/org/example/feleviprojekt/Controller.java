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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Controller {
    @FXML
    AnchorPane topviewPane;
    @FXML
    AnchorPane backviewPane;
    @FXML
    AnchorPane sideviewPane;
    @FXML
    AnchorPane configPane;
    //TODO: Add fx:id to the anchorpanes
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void fullView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fullView.fxml"));
        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void topView(ActionEvent event) throws IOException {
        ObservableList<Node> clock = topviewPane.getChildren();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/backView.fxml"));
        root = loader.load();

        backController backController = loader.getController();
        backController.display(clock);

        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        public void backView(ActionEvent event) throws IOException {
            ObservableList<Node> clock = backviewPane.getChildren();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/topView.fxml"));
            root = loader.load();

            topController topController = loader.getController();
            topController.display(clock);

            stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    public void sideView() {
    }
    public void save() {
        FileChooser savefile = new FileChooser();
        Random rand;
        savefile.setTitle("Fájl mentése...");
        savefile.showSaveDialog(stage);
        //TODO: Implement save function
    }
    public void open() {
        FileChooser openfile = new FileChooser();
        openfile.setTitle("Fájl megnyitása...");
        openfile.showOpenDialog(stage);
        //TODO: Implement open function
    }
    public void generate() {
        //TODO: Implement object random generataion function
    }
    public void help() {
        //TODO: Implement help function
    }
}
