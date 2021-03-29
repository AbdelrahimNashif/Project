package com.example.project;

public class Task {
    private boolean checked;
    private String text;

    public Task(boolean checked, String text) {
        this.checked = checked;
        this.text = text;
    }

    public Task() {
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
