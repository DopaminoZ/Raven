package com.example.oop;

import com.mongodb.MongoWriteException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.VideoTrack;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import static com.example.oop.MongoClientConnection.*;



public class SceneController {
    int i;
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
    private Label userProfileName;
    @FXML
    private ImageView visitprofileimageview;
    @FXML
    private Label visituserProfileName;
    @FXML
    private MediaView testMedia;
    @FXML
    private ImageView testPostImage;
    @FXML
    private static Media selectedVid;
    @FXML
    private static Image selectedImg;
    @FXML
    private Button pauseButton;
    @FXML
    private Button playButton;
    @FXML
    private VBox myvbox;
    private Button followButton;
    @FXML
    private Button unfollowButton;
    @FXML
    private TextField searchBarProfile;
    @FXML
    private VBox followingVBox;
    @FXML
    private VBox Vboxvisit;
    @FXML
    private AnchorPane messagespane;
    @FXML
    private VBox messagesvbox;
    public MediaPlayer medPlayer;
    public static int selector;
    public static User currentUser;
    public static User visitedUser;
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public void pause(MouseEvent event, MediaPlayer selected){
        selected.pause();
    }
    public void play(MouseEvent event, MediaPlayer selected){
        selected.play();
    }

    public void switchtoReg(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("reg_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();

    }
    public void switchtoLogin(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
    public void switchtoForgetPass(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("forgetpass_page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public void switchtoProfile(ActionEvent event) throws IOException{

        root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
        loadUserProfile(currentUser);
    }

    public void switchtoNewsfeed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("newsfeed.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");

        stage.show();
        myvbox = (VBox) scene.lookup("#myvbox");
        myvbox.setPrefWidth(600); // or any other width you prefer
        myvbox.setPrefHeight(800);
        //makeitrain(currentUser.posts.size(),myvbox);
        makeitrain(1,myvbox);

        loadUserProfile(currentUser);
        messagesvbox = (VBox)scene.lookup("#messagesvbox");
        messagesvbox.setPrefWidth(600); // or any other width you prefer
        messagesvbox.setPrefHeight(800);
        makeitrainmessages(10,messagesvbox);
    }
    public void switcher(ActionEvent event,String path) throws IOException{
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
    public void switcher(ActionEvent event,String path,User userData) throws IOException{
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();

    }
    /*public void makeitrainMessages(AnchorPane anchorPane){
        anchorPane = (AnchorPane) scene.lookup("#messagespane");
        if (messagespane != null) {

        } else {
            System.out.println("anchorPane is null");
        }}*/
    public void postMaker(AnchorPane anchorPane, Post post) throws IOException {
        anchorPane.setStyle("-fx-background-color: #000910;"); // Set background color of AnchorPane
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Add some padding to the VBox
        vbox.setSpacing(30);
        HBox profile = new HBox();
        profile.setPadding(new Insets(10));
        Document userData = loadUserData(post.getOwner());
        if(userData == null){
            System.out.println("no user");
            return;
        }
        User owner = userMaker(userData);
        Image sora = loadImage(owner);
        ImageView pfp = new ImageView(sora);
        pfp.setFitHeight(75);
        pfp.setFitWidth(75);
        Label name = new Label(owner.getFirstName() + " " + owner.getLastName());
        profile.getChildren().addAll(pfp,name);
        TextFlow textFlow = new TextFlow();
        Text text = new Text(post.getCaption());
        text.setFill(Color.BLACK); // Set text color
        textFlow.getChildren().add(text);
        StackPane content = new StackPane();
        Image image1 = new Image(getClass().getResourceAsStream("raven_1_invert.png"));
        ImageView imageView = new ImageView();
        imageView.setFitHeight(300);
        imageView.setFitWidth(525);
        imageView.setPreserveRatio(true);
        imageView.setImage(image1);
        imageView.setVisible(false);

        Media video = post.getVid();
        MediaPlayer playz  = new MediaPlayer(video);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(playz);
        mediaView.setFitHeight(300);
        mediaView.setFitWidth(525);
        mediaView.setPreserveRatio(true);
        content.getChildren().addAll(imageView,mediaView);
        // Set the position of the ImageView and MediaView to the same values
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        mediaView.setLayoutX(0);
        mediaView.setLayoutY(0);

        HBox hbox = new HBox();
        hbox.setSpacing(10); // Add some spacing between elements in the HBox
        hbox.setStyle("-fx-background-color: #000910;"); // Set background color of HBox

        ImageView raven1 = new ImageView();
        raven1.setFitHeight(32);
        raven1.setFitWidth(42);
        raven1.setPreserveRatio(true);
        raven1.setImage(new Image(getClass().getResourceAsStream("playButton.png"))); // Set text color to a darker gray
        raven1.setOnMouseClicked(e -> play(e, playz));
        ImageView raven2 = new ImageView();
        raven2.setFitHeight(32);
        raven2.setFitWidth(42);
        raven2.setPreserveRatio(true);
        raven2.setImage(new Image(getClass().getResourceAsStream("pauseButton.png")));
        raven2.setOnMouseClicked(e -> pause(e, playz));
        ImageView raven3 = new ImageView();
        raven3.setFitHeight(32);
        raven3.setFitWidth(42);
        raven3.setPreserveRatio(true);
        raven3.setImage(new Image(getClass().getResourceAsStream("likehollow.png")));
        Label label3 = new Label("Label 3");
        label3.setTextFill(Color.rgb(50, 50, 50)); // Set text color to a darker gray

        hbox.getChildren().addAll(label3,raven1,raven2,raven3);
        vbox.getChildren().addAll(profile,textFlow,content,hbox);
        anchorPane.getChildren().add(vbox);
    }

    public void makeitrain(int num,VBox parent) throws IOException {
        for(int i=0; i<num; i++){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setId("anchor"+i);
            Media x = new Media(getClass().getResource("testVideo.mp4").toString());
            postMaker(anchorPane, new Post("a@gmail.com", "yayayoyo", x));
            parent.getChildren().add(anchorPane);
        }
    }
    public void makeitrainmessages(int num,VBox parent){
        for(int i=0; i<=num; i++){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: #ffffff;-fx-border-color:#0c0c0c");
            anchorPane.setId("anchor"+i);
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10));
            Text message = new Text("database shit");
            message.setStyle("-fx-font-color: #0c0c0c;"); // Set text color
            vbox.getChildren().add(message);
            anchorPane.getChildren().add(vbox);
            parent.getChildren().add(anchorPane);
        }
    }
    public void loadVideoFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Video/Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video/Image Files", "*.mp4", "*.avi", "*.mkv", "*.jpg", "*.jpeg", "*.png")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            File selectedFile = selectedFiles.get(0);
            String fileExtension = getFileExtension(selectedFile);
            if (isVideoFile(fileExtension)) {
                selectedVid = new Media(selectedFile.toURI().toString());
                selector = 1;
            } else if (isImageFile(fileExtension)) {
                selectedImg = new Image(selectedFile.toURI().toString());
                selector = 2;
            }
        }
    }
    public void finalizePost(){
        if(selector == 1){
            medPlayer = new MediaPlayer(selectedVid);
            testMedia.setMediaPlayer(medPlayer);
            medPlayer.play();
            testMedia.setVisible(true);
            playButton.setVisible(true);
            pauseButton.setVisible(true);
            testPostImage.setVisible(false);
        }
        else if(selector == 2){
            testPostImage.setImage(selectedImg);
            if(medPlayer != null)
                medPlayer.stop();
            testPostImage.setVisible(true);
            playButton.setVisible(false);
            pauseButton.setVisible(false);
            testMedia.setVisible(false);
        }
    }
    public void loadNetworkIntoProfile() throws IOException {
        ArrayList<String> following = currentUser.getFriendList();
        for(String friend : following){
            Document ins = loadUserData(friend);
            User y = userMaker(ins);
// Instantiate the AnchorPane
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(200.0);
            anchorPane.setPrefWidth(200.0);
// Instantiate the ImageView
            ImageView imageView = new ImageView(loadImage(y));
            imageView.setFitHeight(75.0);
            imageView.setFitWidth(75.0);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
// Instantiate the Label for the name
            Label nameLabel = new Label(y.getFirstName()+ " " + y.getLastName());
            nameLabel.setLayoutX(135.0);
            nameLabel.setLayoutY(21.0);
// Add the children to the AnchorPane
            anchorPane.getChildren().addAll(imageView, nameLabel);
// Add the Label and AnchorPane to the VBox
            followingVBox.getChildren().addAll(anchorPane);
        }
    }
    public void loadVisitNetworkIntoProfile() throws IOException {
        ArrayList<String> following = visitedUser.getFriendList();
        for(String friend : following){
            Document ins = loadUserData(friend);
            User y = userMaker(ins);
// Instantiate the AnchorPane
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(200.0);
            anchorPane.setPrefWidth(200.0);
// Instantiate the ImageView
            ImageView imageView = new ImageView(loadImage(y));
            imageView.setFitHeight(75.0);
            imageView.setFitWidth(75.0);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
// Instantiate the Label for the name
            Label nameLabel = new Label(y.getFirstName()+ " " + y.getLastName());
            nameLabel.setLayoutX(135.0);
            nameLabel.setLayoutY(21.0);
// Add the children to the AnchorPane
            anchorPane.getChildren().addAll(imageView, nameLabel);
// Add the Label and AnchorPane to the VBox
            Vboxvisit.getChildren().addAll(anchorPane);
        }
    }
    public void searchForUser(ActionEvent event) throws IllegalArgumentException {
        try{
            Document userData = searchForUserByName(searchBarProfile.getText());
            Document userData2 = loadUserData(searchBarProfile.getText());
            if (userData != null) {

                    visitedUser = userMaker(userData);
                    ArrayList<String> test = currentUser.getFriendList();
                    switcher(event, "visitProfile.fxml");
                    followButton = (Button) scene.lookup("#followButton");
                    unfollowButton = (Button) scene.lookup("#unfollowButton");
                if(test!=null){
                    if (test.contains(visitedUser.getEmail())){
                        followButton.setVisible(false);
                        unfollowButton.setVisible(true);
                    }
                    else{
                        followButton.setVisible(true);
                        unfollowButton.setVisible(false);
                    }}
                else{
                    followButton.setVisible(true);
                    unfollowButton.setVisible(false);
                }
                    loadVisitedUserProfile();

            }
            else if(userData2 != null){
                visitedUser = userMaker(userData2);
                ArrayList<String> test  =  currentUser.getFriendList();
                switcher(event, "visitProfile.fxml");
                followButton = (Button) scene.lookup("#followButton");
                unfollowButton = (Button) scene.lookup("#unfollowButton");
                if(test!=null){
                if (test.contains(visitedUser.getEmail())){
                    followButton.setVisible(false);
                    unfollowButton.setVisible(true);
                }
                else{
                    followButton.setVisible(true);
                    unfollowButton.setVisible(false);
                }}
                else{
                    followButton.setVisible(true);
                    unfollowButton.setVisible(false);
                }
                loadVisitedUserProfile();
            }
            else{
                throw new Exception();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void followUser(ActionEvent event) throws IOException {
        ArrayList<String> newFriendList = currentUser.getFriendList();
        if(newFriendList == null)
            newFriendList = new ArrayList<>();
        newFriendList.add(visitedUser.getEmail());
        currentUser.setFriendList(newFriendList);
        updateFriendListInDatabase(currentUser);
        switchtoProfile(event);
    }
    public void unfollowUser(ActionEvent event) throws IOException {
        ArrayList<String> newFriendList = currentUser.getFriendList();
        if(newFriendList == null)
            newFriendList = new ArrayList<>();
        newFriendList.remove(visitedUser.getEmail());
        currentUser.setFriendList(newFriendList);
        updateFriendListInDatabase(currentUser);
        switchtoProfile(event);
    }
    private void updateFriendListInDatabase(User user) {
        Document currentDoc = loadUserData(user.getEmail());
        if (currentDoc != null) {
            ArrayList<String> friendEmails = new ArrayList<>();
            for (String friend : user.getFriendList()) {
                friendEmails.add(friend);
            }
            currentDoc.put("friendList", friendEmails);
            updateUserData(currentDoc);
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    private boolean isVideoFile(String extension) {
        return extension.equals("mp4") || extension.equals("avi") || extension.equals("mkv");
    }

    private boolean isImageFile(String extension) {
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
    }
    public void loadUserProfile(User user){
        try {
            profileimageview = (ImageView) scene.lookup("#profileimageview");
            userProfileName = (Label) scene.lookup("#userProfileName");
            followingVBox = (VBox) scene.lookup("#followingVBox");
            user = currentUser;
            if (user != null) {
                profileimageview.setImage(loadImage(user));
                userProfileName.setText(user.getFirstName() + " " + user.getLastName());
                loadNetworkIntoProfile();
            }
        }
        catch(NullPointerException e){
            System.out.println("Not an issue, ignore trust..");
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    public void loadVisitedUserProfile(){
        try {
            visitprofileimageview = (ImageView) scene.lookup("#visitprofileimageview");
            visituserProfileName = (Label) scene.lookup("#visituserProfileName");
            Vboxvisit = (VBox) scene.lookup("#Vboxvisit");
            if (visitedUser != null) {
                visitprofileimageview.setImage(loadImage(visitedUser));
                visituserProfileName.setText(visitedUser.getFirstName() + " " + visitedUser.getLastName());
                loadVisitNetworkIntoProfile();

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
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
            Document currentDoc = loadUserData(currentUser.getEmail());
            //Making sure as to not rehash the password again.
            if (currentDoc != null) {
                //Replacing just the image
                currentDoc.put("image", currentUser.getImageData());
                /* Debugging: Print the current document before updating
                System.out.println("Updating document: " + currentDoc.toJson());*/
                updateUserData(currentDoc);
                profileimageview.setImage(loadImage(currentUser));
            }
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
        User newUser = new User(userData.getString("userId"),tokens[0],tokens[1],userData.getString("_id"),userData.getString("password"),userData.getDate("dob"),userData.getString("secques"),userData.getString("quesans"), userData.get("image", Binary.class), userData.get("friendList", ArrayList.class));
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
            User newUser = new User(String.valueOf(nextId), firstnamefield.getText(), lastnamefield.getText(), emailField.getText(), passField.getText(), dob, questionField.getText(), answerField.getText(), null, null );
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
                    switchtoNewsfeed(event);
                    loadUserProfile(currentUser);
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
            System.out.println(e.getMessage());
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
