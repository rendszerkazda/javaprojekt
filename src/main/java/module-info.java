module org.example.feleviprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.feleviprojekt to javafx.fxml;
    exports org.example.feleviprojekt;
}