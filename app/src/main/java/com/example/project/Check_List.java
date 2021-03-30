package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Check_List extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private FloatingActionButton fab;
    private Dialog d;
    //tasks list
    private ListView listView;
    private ArrayList<Task> taskArrayList;
    private TaskAdapter taskAdapter;

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference taskListRef;
    private FirebaseAuth auth;
    private String uid;
    private int taskscount;

    private String isChecked;
    private String addedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__list);

        fab = findViewById(R.id.activity_check_list_FAB);
        fab.setOnClickListener(this);
        //firebase
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        taskListRef = firebaseDatabase.getReference("taskLists");
        uid = auth.getCurrentUser().getUid().toString();


        listView = findViewById(R.id.activity_check_list_Listview);
        listView.setOnItemClickListener(this);
        taskArrayList = new ArrayList<Task>();
        taskAdapter = new TaskAdapter(this, 0, taskArrayList);



        //intent from addTaskBottomSheet
        Intent intent = getIntent();
        if (intent.getStringExtra("text") != null) {
            isChecked = intent.getStringExtra("isChecked");
            Log.d("abode", "this is after the intent: "+String.valueOf(isChecked));
            addedText = intent.getStringExtra("text");
            String[] sentences=addedText.split("\n");
            String untilDot=sentences[0];


            taskListRef.child(uid).child("task: "+untilDot).setValue(new Task(isChecked,addedText));
        }



        getAllTasks();

    }

    private void getAllTasks() {

                        taskListRef.child(uid).orderByChild("checked").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                taskArrayList=new ArrayList<Task>();
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    taskArrayList.add(d.getValue(Task.class));
                                }

                                taskAdapter = new TaskAdapter(Check_List.this, 0, taskArrayList);
                                listView.setAdapter(taskAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }







    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {
        if (view == fab) {

            addTaskBottomSheet addTaskBottomSheet = new addTaskBottomSheet();
            addTaskBottomSheet.show(getSupportFragmentManager(), "TAG");


        }
    }
}
