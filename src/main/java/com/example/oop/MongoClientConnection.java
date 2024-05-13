package com.example.oop;


import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Label;
import org.bson.Document;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;


public class MongoClientConnection {
    private static String connectionString = "mongodb+srv://alibagoury:Gold12121$@socialmedia.rhtgxdv.mongodb.net/?retryWrites=true&w=majority&appName=Socialmedia";
    private static MongoClient client = MongoClients.create(connectionString);
    private static MongoDatabase db = client.getDatabase("accountData");
    static MongoCollection accountcol = db.getCollection("accountCollection");
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static void addUserData(User x, Label error){

            Document newUser = new Document("_id", x.getEmail()).append("userId", x.getUserId()).append("password", encodeSHA256(x.getPassword())).
                    append("fullname", x.getFirstName() + " " + x.getLastName()).append("dob", x.getDoB());
            accountcol.insertOne(newUser);
            error.setText("Account successfully created! You can login in now.");
    }
    public static String encodeSHA256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



}
