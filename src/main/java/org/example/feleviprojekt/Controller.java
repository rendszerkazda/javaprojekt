package org.example.feleviprojekt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    AnchorPane topviewPane;
    @FXML
    AnchorPane backviewPane;
    @FXML
    AnchorPane sideviewPane;
    @FXML
    AnchorPane configPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void fullView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fullView.fxml"));
        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println(((MenuItem) event.getSource()).getParentPopup());
    }
    public void topView(ActionEvent event) throws IOException {
        ObservableList<Node> clock = topviewPane.getChildren();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/topView.fxml"));
        root = loader.load();

        topController topController = loader.getController();
        topController.display(clock);

        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void backView(ActionEvent event) throws IOException {
        ObservableList<Node> clock = this.backviewPane.getChildren();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/backView.fxml"));
        root = loader.load();

        backController backController = loader.getController();
        backController.display(clock);

        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void sideView(ActionEvent event) throws IOException {
        ObservableList<Node> clock = sideviewPane.getChildren();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sideView.fxml"));
        root = loader.load();

        sideController sideController = loader.getController();
        sideController.display(clock);

        stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void save() {
        FileChooser savefile = new FileChooser();
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
    @FXML
    private Slider watchFaceSlider;
    @FXML
    private Slider frameSlider;
    @FXML
    private Circle watchFace;
    @FXML
    private Circle frame;
    @FXML
    private Line hourHand;
    @FXML
    private Line minuteHand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: Update these properties on every resize

        watchFaceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                watchFaceSlider.setMax(frame.getRadius()-2);
                frameSlider.setMin(watchFace.getRadius()+5);
                watchFace.setRadius(watchFaceSlider.getValue());
                //TODO: Make the hands follow the watchface size
//                minuteHand.setEndY(watchFace.getRadius()-9);
//                hourHand.setEndY(hourHand.getEndY()+watchFace.getRadius()-14);
//                hourHand.setEndX(hourHand.getEndX()+watchFace.getRadius()-14);

            }
        });
        frameSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                watchFaceSlider.setMax(frame.getRadius()-2);
                frameSlider.setMin(watchFace.getRadius()+5);
                frame.setRadius(frameSlider.getValue());
            }
        });
    }
}
