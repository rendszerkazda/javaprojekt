package org.example.feleviprojekt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private AnchorPane topviewPane;
    @FXML
    private AnchorPane backviewPane;
    @FXML
    private AnchorPane sideviewPane;
    @FXML
    private AnchorPane configPane;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private SplitPane felsoPane;
    @FXML
    private SplitPane alsoPane;
    @FXML
    private SplitPane mainPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Rectangle buckleRec;
    @FXML
    private Line buckleLine;
    @FXML
    private Rectangle backviewStrap;
    @FXML
    private Circle backviewFrame;
    @FXML
    private Rectangle backviewBuckleRec;
    @FXML
    private Line backviewBuckleLine;
    @FXML
    private Rectangle sideviewStrap;
    @FXML
    private Rectangle sideviewBuckle;
    @FXML
    private Rectangle sideviewFrame;
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField minuteTextField;
    @FXML
    private Button currentTimeBtn;
    @FXML
    private Slider watchFaceSlider;
    @FXML
    private Slider frameSlider;
    @FXML
    private Slider strapSlider;
    @FXML
    private Slider timeSlider;
    @FXML
    private Circle watchFace;
    @FXML
    private Circle frame;
    @FXML
    private Rectangle strap;
    @FXML
    private Line hourHand;
    @FXML
    private Line minuteHand;
    //Colorpickers
    @FXML
    private ColorPicker watchFaceColor;
    @FXML
    private ColorPicker frameColor;
    @FXML
    private ColorPicker strapColor;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int hour = 3, minute = 60;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: Update these properties on every resize
        int maxWatchFace = 100;
        //int maxFrame = maxWatchFace+ 3;
        watchFaceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //TODO: Fix weird behaviour if adjusting watchface size, after frame size
                watchFaceSlider.setMax(maxWatchFace);
                watchFace.setRadius(watchFaceSlider.getValue());
                frameSlider.setMax(watchFaceSlider.getValue()+5);
                frameSlider.setValue(watchFaceSlider.getValue()+3);
                ChangeHands();
            }
        });
        frameSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                frameSlider.setMax(maxWatchFace+5);
                frameSlider.setMin(watchFaceSlider.getValue()+3);
                frame.setRadius(frameSlider.getValue());
                backviewFrame.setRadius(frameSlider.getValue());
                sideviewFrame.setWidth(frameSlider.getValue()*2);
                WindowChanged();
            }
        });
        strapSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                strap.setWidth(strapSlider.getValue()*5);
                sideviewStrap.setWidth(strapSlider.getValue()*5);
                backviewStrap.setWidth(strapSlider.getValue()*5);
                WindowChanged();
            }
        });

        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int time = (int)timeSlider.getValue();
                hour = time / 60;
                minute = time % 60;
                hourTextField.setText(Integer.toString(hour));
                minuteTextField.setText(Integer.toString(minute));
                ChangeHands();
            }
        });

        hourTextField.textProperty().addListener((observable, oldvalue, newvalue) -> {
            try {
                hour = Integer.parseInt(hourTextField.getText());
                if (hour > 23) {
                    hourTextField.setText("23");
                    hour = 23;
                }
            } catch (NumberFormatException e) {
                minuteTextField.setText("0");
            }
            ChangeSlider();
            ChangeHands();
        });

        minuteTextField.textProperty().addListener((observable, oldvalue, newvalue) -> {
            try {
                minute = Integer.parseInt(minuteTextField.getText());
                if (minute > 59) {
                    minuteTextField.setText("59");
                    minute = 59;
                }
            } catch (NumberFormatException e) {
                minuteTextField.setText("0");
            }
            ChangeSlider();
            ChangeHands();
        });

        currentTimeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
                hour = currentDate.getHours();
                minute = currentDate.getMinutes();
                ChangeSlider();
            }
        });

        felsoPane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                alsoPane.setDividerPositions(felsoPane.getDividerPositions());
                WindowChanged();
            }
        });

        alsoPane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                felsoPane.setDividerPositions(alsoPane.getDividerPositions());
                WindowChanged();
            }
        });

        mainPane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                WindowChanged();
            }
        });
        WindowChanged();
        currentTimeBtn.fire();
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

    public void wfColorPick(){
        watchFace.setFill(watchFaceColor.getValue());
    }
    public void frameColorPick(){
        frame.setFill(frameColor.getValue());
        backviewFrame.setFill(frameColor.getValue());
        sideviewFrame.setFill(frameColor.getValue());
    }
    public void strapColorPick(){
        strap.setFill(strapColor.getValue());
        backviewStrap.setFill(strapColor.getValue());
        sideviewStrap.setFill(strapColor.getValue());
    }
    private void ChangeSlider() {timeSlider.setValue(hour * 60 + minute);}

    private void ChangeHands() {
        double minuteHandLength = watchFace.getRadius() * .75;
        double hourHandLength = watchFace.getRadius() * .35;

        double minuteAngle = (double)(360/60) * ((minute == 60) ? 0 : minute);
        double hourAngle = (double)(360/12) * ((hour == 12) ? 0 : hour);

        // Nagymutató
        minuteHand.setStartX(topviewPane.getWidth() / 2);
        minuteHand.setStartY(topviewPane.getHeight() / 2);
        minuteHand.setEndX(minuteHand.getStartX() + minuteHandLength * Math.sin(Math.toRadians(minuteAngle)));
        minuteHand.setEndY(minuteHand.getStartY() - minuteHandLength * Math.cos(Math.toRadians(minuteAngle)));

        // Kismutató
        hourHand.setStartX(topviewPane.getWidth() / 2);
        hourHand.setStartY(topviewPane.getHeight() / 2);
        hourHand.setEndX(hourHand.getStartX() + hourHandLength * Math.sin(Math.toRadians(hourAngle)));
        hourHand.setEndY(hourHand.getStartY() - hourHandLength * Math.cos(Math.toRadians(hourAngle)));
    }

    private void WindowChanged() {
        // TODO: put it into separate functions (like: topViewRedraw or something like that)
        //
        // Felülnézet
        //
        // Keret
        frame.setCenterX(topviewPane.getWidth() / 2);
        frame.setCenterY(topviewPane.getHeight() / 2);

        // Számlap
        watchFace.setCenterX(topviewPane.getWidth() / 2);
        watchFace.setCenterY(topviewPane.getHeight() / 2);

        // Szíj
        strap.setX(topviewPane.getWidth() / 2 - strap.getWidth() / 2);
        strap.setY(topviewPane.getHeight() / 2 - strap.getHeight() / 2);

        ChangeHands();

        // Csat
        buckleRec.setX(strap.getX() + strap.getWidth());
        buckleRec.setY(strap.getY());

        // Csat pöcök
        buckleLine.setStartX(strap.getX() + strap.getWidth());
        buckleLine.setStartY(strap.getY() + buckleRec.getHeight() / 2);
        buckleLine.setEndX(buckleLine.getStartX() + buckleRec.getWidth() / 3);
        buckleLine.setEndY(buckleLine.getStartY());

        //
        // Hátoldali nézet
        //
        // Keret
        backviewFrame.setCenterX(backviewPane.getWidth() / 2);
        backviewFrame.setCenterY(backviewPane.getHeight() / 2);

        // Szíj
        backviewStrap.setX(backviewPane.getWidth() / 2 - backviewStrap.getWidth() / 2);
        backviewStrap.setY(backviewPane.getHeight() / 2 - backviewStrap.getHeight() / 2);

        // Csat
        backviewBuckleRec.setX(backviewStrap.getX() + backviewStrap.getWidth());
        backviewBuckleRec.setY(backviewStrap.getY());

        // Csat pöcök
        backviewBuckleLine.setStartX(backviewStrap.getX() + backviewStrap.getWidth());
        backviewBuckleLine.setStartY(backviewStrap.getY() + backviewBuckleRec.getHeight() / 2);
        backviewBuckleLine.setEndX(backviewBuckleLine.getStartX() + backviewBuckleRec.getWidth() / 3);
        backviewBuckleLine.setEndY(backviewBuckleLine.getStartY());

        //
        // Oldalnézet
        //
        // Szíj
        sideviewStrap.setX(sideviewPane.getWidth() / 2 - sideviewStrap.getWidth() / 2);
        sideviewStrap.setY(sideviewPane.getHeight() / 2 - sideviewStrap.getHeight() / 2);

        // Keret
        sideviewFrame.setY(sideviewStrap.getY() - sideviewFrame.getHeight());
        sideviewFrame.setX(sideviewPane.getWidth() / 2 - sideviewFrame.getWidth() / 2);

        sideviewBuckle.setX(sideviewStrap.getX() + sideviewStrap.getWidth() - 1);
        sideviewBuckle.setY(sideviewStrap.getY());
    }

}
