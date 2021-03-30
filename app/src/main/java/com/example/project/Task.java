package com.example.project;

public class Task {
    private String checked;
    private String text;

    public Task(String checked, String text) {
        this.checked = checked;
        this.text = text;
    }

    public Task() {
    }

    public String isChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
