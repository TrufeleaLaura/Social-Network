module com.example.social_network_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.social_network_gui to javafx.fxml;
    exports com.example.social_network_gui;
}