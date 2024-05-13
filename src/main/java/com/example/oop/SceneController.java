package com.example.oop;

import com.mongodb.MongoWriteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Objects;
import java.util.regex.Pattern;
import static com.example.oop.MongoClientConnection.*;



public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private TextField firstnamefield;
    @FXML
    private TextField lastnamefield;
    @FXML
    private TextField confirmfield;
    @FXML
    private TextField loginemail;
    @FXML
    private TextField loginpass;
    @FXML
    private DatePicker dobfield;
    @FXML
    private Label errorchecklog;
    @FXML
    private Label errorcheck;


    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

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
        try {
            long count = accountcol.countDocuments();
            int nextId = (int) (count + 1);
            LocalDate x = dobfield.getValue();
            Date dob = Date.valueOf(x);
            if(!Objects.equals(passField.getText(), confirmfield.getText()))
                throw new DifferentPasswordException();
            if(!isValidEmail(emailField.getText()))
                throw new InvalidEmailException();
            User newUser = new User(String.valueOf(nextId), firstnamefield.getText(), lastnamefield.getText(), emailField.getText(), passField.getText(), dob);
            addUserData(newUser, errorcheck);
            System.out.println(newUser.getUserId() + newUser.getDoB() + newUser.getEmail() + newUser.getPassword());
        }
        catch(InvalidEmailException | DifferentPasswordException e){
            errorchecklog.setText(e.getMessage());
        }
        catch(MongoWriteException e){
            errorchecklog.setText("Email already exists, try logging in instead.");
        }
        catch(Exception e){
            errorchecklog.setText("Invalid credentials, try again.");
        }
    }
    public void login(ActionEvent event) throws IOException{
        try{
            Document userData = loadUserData(loginemail.getText());
            if (userData != null) {
                if(Objects.equals(userData.getString("password"), encodeSHA256(loginpass.getText())))
                 errorchecklog.setText("Login successful!");
                else
                    throw new InvalidPasswordException();
            } else {
                throw new Exception();
            }
        }
        catch(InvalidPasswordException e){
            errorchecklog.setText(e.getMessage());
        }
        catch (Exception e){
            errorchecklog.setText("No account exists with that email.");
        }
    }
    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }


}
