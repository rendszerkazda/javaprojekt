package org.example.feleviprojekt;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    Pane felulNezet;
    Pane alulNezet;
    Pane oldalNezet;
    Pane konfig;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void teljesNezet() {
        System.out.println("Hello World!!");
    }
    public void felulNezet(ActionEvent event) throws IOException {
        ObservableList<Node> ora = felulNezet.getChildren();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/felulNezet.fxml"));
        root = loader.load();

        FelulnezetController felulnezetController = loader.getController();
        felulnezetController.display(ora);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void alulNezet() {
        System.out.println("Hello World!!");
    }
}
