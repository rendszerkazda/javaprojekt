package org.example.feleviprojekt;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
//region FXML injection
    //viewPanes
    @FXML
    private AnchorPane topviewPane;
    @FXML
    private AnchorPane backviewPane;
    @FXML
    private AnchorPane sideviewPane;
    //SplitPanes
    @FXML
    private SplitPane felsoPane;
    @FXML
    private SplitPane alsoPane;
    @FXML
    private SplitPane mainPane;
    //Shapes
    @FXML
    private Circle watchFace;
    @FXML
    private Circle frame;
    @FXML
    private Rectangle strap;
    @FXML
    private Rectangle buckleRec;
    @FXML
    private Line buckleLine;
    @FXML
    private Line hourHand;
    @FXML
    private Line minuteHand;
    //Backview Shapes
    @FXML
    private Rectangle backviewStrap;
    @FXML
    private Circle backviewFrame;
    @FXML
    private Rectangle backviewBuckleRec;
    @FXML
    private Line backviewBuckleLine;
    //Sideview Shapes
    @FXML
    private Rectangle sideviewStrap;
    @FXML
    private Rectangle sideviewBuckle;
    @FXML
    private Rectangle sideviewFrame;
    //Textfields
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField minuteTextField;
    @FXML
    private Button currentTimeBtn;
    //Sliders
    @FXML
    private Slider watchFaceSlider;
    @FXML
    private Slider frameSlider;
    @FXML
    private Slider strapSlider;
    @FXML
    private Slider timeSlider;
    //Colorpickers
    @FXML
    private ColorPicker watchFaceColor;
    @FXML
    private ColorPicker frameColor;
    @FXML
    private ColorPicker strapColor;
//endregion
    private Stage stage;
    private int hour = 3, minute = 60;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int maxWatchFace = 70;
        int maxFrame = maxWatchFace + 3;
        watchFaceSlider.setMax(maxWatchFace);
        frameSlider.setMax(maxWatchFace+5);
        watchFaceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                frameSlider.setMax(watchFaceSlider.getValue()+5);
                watchFace.setRadius(watchFaceSlider.getValue());
                frameSlider.setValue(watchFaceSlider.getValue()+3);
                ChangeHands();
            }
        });
        frameSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                frameSlider.setMin(watchFaceSlider.getValue()+1);
                int maxFrameSize = (int) ((watchFaceSlider.getValue()<30) ? (watchFace.getRadius()+3) : watchFace.getRadius() + 25);
                frameSlider.setMax(maxFrameSize);
                frame.setRadius(frameSlider.getValue());
                backviewFrame.setRadius(frameSlider.getValue());
                sideviewFrame.setWidth(frameSlider.getValue()*2);
                WindowChanged();
            }
        });
        strapSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                strap.setWidth(strapSlider.getValue());
                sideviewStrap.setWidth(strapSlider.getValue());
                backviewStrap.setWidth(strapSlider.getValue());
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
            ChangeSliders();
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
            ChangeSliders();
            ChangeHands();
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
        //First open stuff
        WindowChanged();
        currentTimeBtn.fire();
        generateAll();
    }
    public void save() {
        try {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Fájl mentése...");
            savefile.setInitialFileName("karora"+ (int)timeSlider.getValue() + ".ora");
            savefile.setInitialDirectory(new File(System.getProperty("user.dir")+"/src/main/saved"));
            FileOutputStream file = new FileOutputStream(savefile.showSaveDialog(stage));
            file.write((watchFace.getRadius() + " " + watchFaceColor.getValue() + "\n").getBytes());
            file.write((frame.getRadius() + " " + frameColor.getValue() + "\n").getBytes());
            file.write((strap.getWidth() + " " + strapColor.getValue() + "\n").getBytes());
            file.write((hour * 60 + minute + "\n").getBytes());
            file.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public void open()  {
        double watchFaceRadius = 0;
        double frameRadius = 0;
        double strapWidth = 0;
        String[] colors = new String[3];
        try {
            FileChooser openfile = new FileChooser();
            openfile.setInitialDirectory(new File(System.getProperty("user.dir")+"/src/main/saved"));
            openfile.setTitle("Fájl megnyitása...");
            Scanner file = new Scanner(new FileInputStream(openfile.showOpenDialog(stage)));
            while(file.hasNextLine()){
                String[] line = file.nextLine().split(" ");
                watchFaceRadius = Double.parseDouble(line[0]);
                colors[0] = line[1];
                line = file.nextLine().split(" ");
                frameRadius = Double.parseDouble(line[0]);
                colors[1] = line[1];
                line = file.nextLine().split(" ");
                strapWidth = Double.parseDouble(line[0]);
                colors[2] = line[1];
                line = file.nextLine().split(" ");
                hour = Integer.parseInt(line[0]) / 60;
                minute = Integer.parseInt(line[0]) % 60;

                if (watchFaceRadius == 0 || frameRadius == 0 || strapWidth == 0) {
                    throw new Exception(
                        "A fájlban hibás adatok találhatók! " +
                        "Az óra alapértelmezett értékekre lett állítva!"
                    );
                }
                file.close();
                ChangeSliders(watchFaceRadius,frameRadius,strapWidth);
                ChangeSliders();
                watchFaceColor.setValue(Color.valueOf(colors[0]));
                frameColor.setValue(Color.valueOf(colors[1]));
                strapColor.setValue(Color.valueOf(colors[2]));
                wfColorPick();
                frameColorPick();
                strapColorPick();
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("A fájl nem található!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void generateColor() {
        Random rc = new Random();
        int[] colors = new int[9];
        for (int i = 0; i < colors.length; i++) {colors[i] = rc.nextInt(256);}
        watchFaceColor.setValue(Color.rgb(colors[0], colors[1], colors[2]));
        frameColor.setValue(Color.rgb(colors[3], colors[4], colors[5]));
        strapColor.setValue(Color.rgb(colors[6], colors[7], colors[8]));
        wfColorPick();
        frameColorPick();
        strapColorPick();
    }
    public void generateShape() {
        Random rn = new Random();
        final double maxFace = watchFaceSlider.getMax();
        final double maxFrame = frameSlider.getMax();
        final double maxStrap = strapSlider.getMax();
        final double minFace = watchFaceSlider.getMin();
        final double minFrame = frameSlider.getMin();
        final double minStrap = strapSlider.getMin();
        double watchFaceRadius = rn.nextDouble(minFace,maxFace);
        double frameRadius = rn.nextDouble(minFrame,maxFrame);
        double strapWidth = rn.nextDouble(minStrap,maxStrap);
        watchFace.setRadius(watchFaceRadius);
        frame.setRadius(frameRadius);
        backviewFrame.setRadius(frameRadius);
        sideviewFrame.setWidth(frameRadius*2);
        strap.setWidth(strapWidth);
        sideviewStrap.setWidth(strapWidth);
        backviewStrap.setWidth(strapWidth);
        ChangeSliders(watchFaceRadius,frameRadius,strapWidth);
        WindowChanged();
    }
    public void generateAll(){
        generateColor();
        generateShape();
    }
    public void help() {
        //TODO: Implement help function
    }
    public void exactTime(){
        Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
        hour = currentDate.getHours();
        minute = currentDate.getMinutes();
        ChangeSliders();
    }
    public void resetView(){
        mainPane.setDividerPositions(0.5);
        alsoPane.setDividerPositions(0.5);
        felsoPane.setDividerPositions(0.5);
        alsoPane.setScaleX(1);
        alsoPane.setScaleY(1);
        mainPane.setScaleX(1);
        mainPane.setScaleY(1);
        new Thread(() -> {
            try {
                Thread.sleep(50); // Wait for the UI updates to be processed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                WindowChanged();
            });
        }).start();
    }
    public void topFocus(){
        mainPane.setDividerPositions(1);
        alsoPane.setDividerPositions(1);
        mainPane.setScaleX(1.5);
        mainPane.setScaleY(1.5);
        new Thread(() -> {
            try {
                Thread.sleep(20); // Wait for the UI updates to be processed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                WindowChanged();
            });
        }).start();
    }
    public void backFocus(){
        mainPane.setDividerPositions(0);
        alsoPane.setDividerPositions(1);
        alsoPane.setScaleX(1.5);
        alsoPane.setScaleY(1.5);
        new Thread(() -> {
            try {
                Thread.sleep(20); // Wait for the UI updates to be processed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                WindowChanged();
            });
        }).start();
    }
    public void sideFocus(){
        mainPane.setDividerPositions(1);
        alsoPane.setDividerPositions(0);
        mainPane.setScaleX(1.5);
        mainPane.setScaleY(1.5);
        new Thread(() -> {
            try {
                Thread.sleep(20); // Wait for the UI updates to be processed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                WindowChanged();
            });
        }).start();
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
    private void ChangeSliders(){timeSlider.setValue(hour * 60 + minute);}
    private void ChangeSliders(double watchFaceRadius, double frameRadius, double strapWidth) {
        timeSlider.setValue(hour * 60 + minute);
        watchFaceSlider.setValue(watchFaceRadius);
        frameSlider.setValue(frameRadius);
        strapSlider.setValue(strapWidth);
    }
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
