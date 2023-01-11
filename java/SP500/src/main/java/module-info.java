module com.example.sp500 {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires junit;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.sp500 to javafx.fxml;
    exports com.example.sp500;
    exports com.example.sp500.tests;
    opens com.example.sp500.tests to javafx.fxml;
    exports com.example.sp500.histogram;
    opens com.example.sp500.histogram to javafx.fxml;
}