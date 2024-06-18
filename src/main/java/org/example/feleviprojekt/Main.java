package org.example.feleviprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    // A program belépési pontja
    public static void main(String[] args) {
        // Indítja az alkalmazást
        launch(args);
    }
    // Az alkalmazás indításakor hívódik meg
    @Override
    public void start(Stage stage) throws Exception {
        // Betölti a kezdőképernyőt a megadott FXML fájlból
        Parent root = FXMLLoader.load(getClass().getResource("/fullView.fxml"));
        // Létrehoz egy új jelenetet a betöltött kezdőképernyővel
        Scene scene = new Scene(root);
        // Betölti az ikont a megadott fájlból
        URL url = getClass().getResource("/karoralogo.jpg");
        Image icon = new Image(url.toString());
        // Hozzáadja az ikont a színpadhoz
        stage.getIcons().add(icon);
        // Beállítja a jelenetet a színpadon
        stage.setScene(scene);
        // Beállítja a színpad címét
        stage.setTitle("JaWatch");
        // Megjeleníti a színpadot
        stage.show();
    }
}