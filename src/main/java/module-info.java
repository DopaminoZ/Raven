module com.example.oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.sql;


    opens com.example.oop to javafx.fxml;
    exports com.example.oop;
}