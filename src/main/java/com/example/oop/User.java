package com.example.oop;

import org.bson.types.Binary;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date DoB;
    private String securityQuestion;
    private String questionAnswer;
    private Binary imageData;
    private ArrayList<String> friendList = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();

    public User(String userId, String firstName, String lastName, String email, String password, Date doB, String question, String answer, Binary image) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        DoB = doB;
        securityQuestion = question;
        questionAnswer = answer;
        imageData = image;
    }
    public User(String userId, String firstName, String lastName, String email, String password, Date doB, String question, String answer, Binary image, ArrayList<String> friends) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        DoB = doB;
        securityQuestion = question;
        questionAnswer = answer;
        imageData = image;
        friendList = friends;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Binary getImageData() {
        return imageData;
    }

    public void setImageData(Binary imageData) {
        this.imageData = imageData;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDoB() {
        return DoB;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public String getQuestionAnswer() {
        return questionAnswer;
    }
}
