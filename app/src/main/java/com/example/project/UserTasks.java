package com.example.project;

import java.util.ArrayList;

public class UserTasks {
    private String uid;
    private ArrayList<Task> taskArrayList;

    public UserTasks(String uid, ArrayList<Task> taskArrayList) {
        this.uid = uid;
        this.taskArrayList = taskArrayList;
    }

    public UserTasks() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public void setTaskArrayList(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }
}
