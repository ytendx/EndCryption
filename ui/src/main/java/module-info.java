module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.desktop;


    opens de.ytendx.endcryption.ui to javafx.fxml;
    exports de.ytendx.endcryption.ui;
}