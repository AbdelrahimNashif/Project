package com.example.project;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Startup  implements Serializable{
    private String image;
    private String key,uid,title,subtitle,text;

    public Startup(String key,String uid,String image, String title, String subtitle, String text) {
        this.key=key;
        this.uid=uid;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }
    public Startup(){

}



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String  toString() {
        return "Startup{" +
                "image=" + image +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}


