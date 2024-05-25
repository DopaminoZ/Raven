package com.example.oop;
import javafx.scene.image.Image;
import javafx.scene.media.*;

import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String owner;
    private String caption;
    private Media vid;
    private Image image;
    private ArrayList<String> likes;
    private Date dateCreated;

    public Post(String owner, String caption, Media vid) {
        this.owner = owner;
        this.caption = caption;
        this.vid = vid;
        image = null;
        dateCreated = new Date();
        likes = new ArrayList<>();
    }
    public Post(String owner, String caption, Image img) {
        this.owner = owner;
        this.caption = caption;
        this.image = img;
        vid = null;
        dateCreated = new Date();
        likes = new ArrayList<>();
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

    public Date getDateCreated() {
        return dateCreated;
    }
}
