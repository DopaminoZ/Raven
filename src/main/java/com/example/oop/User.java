package com.example.oop;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<User> friendList = new ArrayList<>();
    private Date DoB;
    private String securityQuestion;
    private String questionAnswer;
    private ArrayList<Post> posts = new ArrayList<>();

    public User(String userId, String firstName, String lastName, String email, String password, Date doB, String question, String answer) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        DoB = doB;
        securityQuestion = question;
        questionAnswer = answer;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;

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
