package com.example.oop;


import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;


public class MongoClientConnection {
    private static String connectionString = "mongodb+srv://alibagoury:Gold12121$@socialmedia.rhtgxdv.mongodb.net/?retryWrites=true&w=majority&appName=Socialmedia";
    private static MongoClient client = MongoClients.create(connectionString);
    private static MongoDatabase db = client.getDatabase("accountData");
    static MongoCollection accountcol = db.getCollection("accountCollection");
    static MongoCollection postcol = db.getCollection("postCollection");
    public static void addUserData(User x, Label error){
            Document newUser = new Document("_id", x.getEmail()).append("userId", x.getUserId()).append("password", encodeSHA256(x.getPassword())).
                    append("fullname", x.getFirstName() + " " + x.getLastName()).append("dob", x.getDoB()).append("secques", x.getSecurityQuestion()).append("quesans", encodeSHA256(x.getQuestionAnswer())).append("image", null).append("friendList", null).append("posts", null);
            accountcol.insertOne(newUser);
            error.setText("Account successfully created! You can login in now.");
    }
    public static void addPost(Post post, File file) throws IOException {
        Binary image = storeImage(file);
        Document newPost = new Document("_id", postcol.countDocuments()+1).append("owner", post.getOwner()).append("caption",post.getCaption()).append("vid", null).append("img", image).append("likes", null).append("dateC", post.getDateCreated());
        postcol.insertOne(newPost);
    }
    public static void updateUserData(Document user){
        accountcol.replaceOne(Filters.eq("_id", user.getString("_id")), user);
    }
    public static Document loadUserData(String email){
        Document query = new Document("_id", email);
        Document userDocument = (Document) accountcol.find(query).first();
        //Console text to check found data on db
                /*for (String key : userDocument.keySet()) {
                    System.out.println(key + ": " + userDocument.get(key));
                }*/
        return userDocument;
    }
    public static ArrayList<Document> loadPosts(String email) {
        Document query = new Document("owner", email);
        MongoCursor<Document> cursor = postcol.find(query).iterator();
        ArrayList<Document> userDocuments = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document userDocument = cursor.next();
                userDocuments.add(userDocument);
            }
        } finally {
            cursor.close();
        }
        return userDocuments;
    }
    public static Document searchForUserByName(String fullname){
        Document query = new Document("fullname", fullname);
        Document userDocument = (Document) accountcol.find(query).first();
        return userDocument;
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
    public static Binary storeImage(String Path) throws IOException {
        File imageFile = new File(Path);
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] imageBytes = new byte[(int) imageFile.length()];
        fis.read(imageBytes);
        fis.close();
        return new Binary(imageBytes);
    }
    public static Binary storeImage(File selectedFile) throws IOException {
        FileInputStream fis = new FileInputStream(selectedFile);
        byte[] imageBytes = new byte[(int) selectedFile.length()];
        fis.read(imageBytes);
        fis.close();
        return new Binary(imageBytes);
    }
    public static Image loadImage(User user) throws IOException {

            Binary imageBinary = user.getImageData();
            if (imageBinary != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBinary.getData());
                Image image = new Image(bis);
                return image;
            }
            else{
                return new Image(MongoClientConnection.class.getResourceAsStream("default_profile.jpg"));
            }

    }
}
