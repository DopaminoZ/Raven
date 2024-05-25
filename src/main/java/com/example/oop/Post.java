package com.example.oop;
import javafx.scene.image.Image;
import javafx.scene.media.*;

import java.util.ArrayList;

public class Post {
    private User owner;
    private String caption;
    private Media vid;
    private Image image;
    private ArrayList<User> likes = new ArrayList<>();

    public Post(User owner, String caption, Media vid) {
        this.caption = caption;
        this.vid = vid;
        image = null;
    }
    public Post(User owner, String caption, Image img) {
        this.caption = caption;
        this.image = img;
        vid = null;
    }
}
