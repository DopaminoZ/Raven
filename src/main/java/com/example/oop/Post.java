package com.example.oop;
import javafx.scene.image.Image;
import javafx.scene.media.*;

import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String postID;
    private String owner;
    private String caption;
    private Media vid;
    private Image image;
    private ArrayList<String> likes;
    private Date dateCreated;

    public Post(String postID, String owner, String caption, Media vid) {
        this.postID = postID;
        this.owner = owner;
        this.caption = caption;
        this.vid = vid;
        image = null;
        likes = new ArrayList<>();
    }
    public Post(String postID, String owner, String caption, Image img, Date date) {
        this.postID = postID;
        this.owner = owner;
        this.caption = caption;
        this.image = img;
        dateCreated = date;
        vid = null;
        likes = new ArrayList<>();
    }

    public Post(String postID, String userEmail, String caption, Image image, Date dateC, ArrayList<String> likes) {
        this.postID = postID;
        this.owner = userEmail;
        this.caption = caption;
        this.image = image;
        dateCreated = dateC;
        vid = null;
        this.likes = likes;
    }

    public String getPostID() {
        return postID;
    }

    public String getOwner() {
        return owner;
    }

    public String getCaption() {
        return caption;
    }

    public Media getVid() {
        return vid;
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
