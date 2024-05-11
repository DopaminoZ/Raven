package com.example.oop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

import static com.example.oop.MongoClientConnection.writeData;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    public void switchtoReg(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("reg_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoLog(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void registerAccount(ActionEvent event) throws IOException{
        long count = MongoClientConnection.col.countDocuments();
        int nextId = (int) (count + 1);
        String email = emailField.getText();
        String password = passField.getText();
        Document newAcc = new Document("_id", nextId).append("email", email);
        newAcc.append("password", password);
        writeData(newAcc);
    }
}
