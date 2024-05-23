package com.example.oop;

import com.mongodb.MongoWriteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.File;
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
    private TextField confirmpassField;
    @FXML
    private TextField loginemail;
    @FXML
    private TextField loginpass;
    @FXML
    private DatePicker dobfield;
    @FXML
    private Label errorchecklog;
    @FXML
    private Label errorcheckLabel;
    @FXML
    private TextField questionField;
    @FXML
    private TextField answerField;
    @FXML
    private Label securityQuestionLabel;
    @FXML
    private Label newpassLabel;
    @FXML
    private Label newconfirmLabel;
    @FXML
    private Label forgetLabel;
    @FXML
    private TextField newpassField;
    @FXML
    private TextField newpassconfirmField;
    @FXML
    private TextField forgetAnswerField;
    @FXML
    private TextField forgetEmailField;
    @FXML
    private Button forgetpassButton;
    @FXML
    private ImageView profileimageview;
    @FXML
    private ImageView profileImageHolder;
    public static User currentUser;
    @FXML
    Image myimage = new Image(getClass().getResourceAsStream("ca30b61e5f6e480229a932e7e87f9787.jpg"));
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public void switchtoReg(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("reg_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoLogin(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoForgetPass(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("forgetpass_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoprofile(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switcher(ActionEvent event,String path) throws IOException{
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switcher(ActionEvent event,String path,User userData) throws IOException{
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void uploadImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            currentUser.setImageData(storeImage(filePath));
            updateUserData(reverseUserMaker(currentUser));
            profileImageHolder.setImage(loadImage(currentUser));

        }

    }
    public Document reverseUserMaker(User x){
        Document userData;
        userData = new Document("_id", x.getEmail()).append("userId", x.getUserId()).append("password", encodeSHA256(x.getPassword())).
                append("fullname", x.getFirstName() + " " + x.getLastName()).append("dob", x.getDoB()).append("secques", x.getSecurityQuestion()).append("quesans", encodeSHA256(x.getQuestionAnswer())).append("image",x.getImageData());
        return userData;
    }
    public User userMaker(Document userData){
        String fullName = userData.getString("fullname");
        String[] tokens = fullName.split(" ");
        User newUser = new User(userData.getString("userId"),tokens[0],tokens[1],userData.getString("_id"),userData.getString("password"),userData.getDate("dob"),userData.getString("secques"),userData.getString("quesans"), userData.get("image", Binary.class));
        return newUser;
    }
    public void registerAccount(ActionEvent event) throws IOException{
        try {
            long count = accountcol.countDocuments();
            int nextId = (int) (count + 1);
            LocalDate x = dobfield.getValue();
            Date dob = Date.valueOf(x);
            if(!Objects.equals(passField.getText(), confirmpassField.getText()))
                throw new DifferentPasswordException();
            if(!isValidEmail(emailField.getText()))
                throw new InvalidEmailException();
            User newUser = new User(String.valueOf(nextId), firstnamefield.getText(), lastnamefield.getText(), emailField.getText(), passField.getText(), dob, questionField.getText(), answerField.getText(), null);
            addUserData(newUser, errorcheckLabel);
            switcher(event,"login_page.fxml", currentUser);
            //System.out.println(newUser.getUserId() + newUser.getDoB() + newUser.getEmail() + newUser.getPassword());
        }
        catch(InvalidEmailException | DifferentPasswordException e){
            errorcheckLabel.setText(e.getMessage());
        }
        catch(MongoWriteException e){
            errorcheckLabel.setText("Email already exists, try logging in instead.");
        }
        catch(Exception e){
            errorcheckLabel.setText("Invalid credentials, try again.");
        }
    }
    public void login(ActionEvent event) throws IOException{
        try{
            Document userData = loadUserData(loginemail.getText());
            if (userData != null) {
                if(Objects.equals(userData.getString("password"), encodeSHA256(loginpass.getText()))) {
                    errorchecklog.setText("Login successful!");
                    currentUser = userMaker(userData);
                    switcher(event, "newsfeed.fxml",currentUser);
                }
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
    public void forgetPassword(ActionEvent event) throws IOException{
        try{
            Document userData = loadUserData(forgetEmailField.getText());
            if (userData != null) {
                    securityQuestionLabel.setText(userData.getString("secques"));
                    forgetpassButton.setDisable(false);
                    forgetAnswerField.setDisable(false);
                    newconfirmLabel.setDisable(false);
                    newpassLabel.setDisable(false);
                    newpassField.setDisable(false);
                    newpassconfirmField.setDisable(false);
                    forgetEmailField.setDisable(true);
                    forgetLabel.setText("");
            } else {
                throw new Exception();
            }
        }
        catch (Exception e){
            forgetLabel.setText("No account exists with that email.");
        }
    }
    public void answerSecQues(ActionEvent event) throws IOException{
        try{
            Document userData = loadUserData(forgetEmailField.getText());
            if(!Objects.equals(encodeSHA256(forgetAnswerField.getText()), userData.getString("quesans")))
                throw new Exception();
            else if(!Objects.equals(newpassconfirmField.getText(), newpassField.getText()))
                throw new DifferentPasswordException();
            else {
                forgetpassButton.setDisable(true);
                userData.put("password", encodeSHA256(newpassField.getText()));
                updateUserData(userData);
                forgetLabel.setText("Password has been reset, you can login now.");
            }
        }
        catch (DifferentPasswordException e){
            forgetLabel.setText(e.getMessage());
        }
        catch (Exception e){
            forgetLabel.setText("Wrong answer.");
        }
    }
    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }


}
