package com.example.project;

import android.graphics.Bitmap;

public class Startup {
    private Bitmap image;
    private String title,subtitle,text;

    public Startup(Bitmap image, String title, String subtitle, String text) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
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


