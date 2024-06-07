package org.example.feleviprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fullView.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("file:src/karoralogo.jpg");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Félévi Java projekt: Karóra");
        stage.show();

    }
}