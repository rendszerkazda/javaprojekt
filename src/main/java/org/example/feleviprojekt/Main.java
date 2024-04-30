package org.example.feleviprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root,600,600,Color.DARKSEAGREEN);
        Image icon = new Image("file:src/karoralogo.jpg");

        Text text = new Text("Hello World!!");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Verdana", 50));
        text.setFill(Color.RED);


        Circle orakeret = new Circle(300,300,90);
        orakeret.setCenterX(300);
        orakeret.setCenterY(300);
        orakeret.setRadius(200);
        orakeret.setFill(Color.BLACK);

        Line percmutato = new Line();
        percmutato.setStartX(300);
        percmutato.setStartY(300);
        percmutato.setEndX(300);
        percmutato.setEndY(130);
        percmutato.setStroke(Color.BLACK);
        percmutato.setStrokeWidth(10);

        Line oramutato = new Line();
        oramutato.setStartX(300);
        oramutato.setStartY(300);
        oramutato.setEndX(340);
        oramutato.setEndY(200);
        oramutato.setStroke(Color.BLACK);
        oramutato.setStrokeWidth(10);

        Circle ora = new Circle(300,300,80);
        ora.setCenterX(300);
        ora.setCenterY(300);
        ora.setRadius(190);
        ora.setFill(Color.CORNSILK);

        root.getChildren().add(text);
        root.getChildren().add(orakeret);
        root.getChildren().add(ora);
        root.getChildren().add(percmutato);
        root.getChildren().add(oramutato);

        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Félévi Java projekt: Karóra");
        stage.show();
    }
}