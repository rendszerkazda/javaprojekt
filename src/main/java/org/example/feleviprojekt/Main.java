package org.example.feleviprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fullView.fxml"));
        Scene scene = new Scene(root);
        URL url = getClass().getResource("/karoralogo.jpg");
        Image icon = new Image(url.toString());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("JaWatch");
        stage.show();
    }
}