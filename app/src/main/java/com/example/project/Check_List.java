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

    private Boolean check;
    private Boolean isChecked;
    private String addedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__list);
        check = false;
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
        Log.d("abode", "before intent if");

        //intent from addTaskBottomSheet
        Intent intent = getIntent();
        if (intent.getStringExtra("text") != null) {
            Log.d("abode", "in intent if");
            isChecked = Boolean.parseBoolean(intent.getStringExtra("isChecked"));
            addedText = intent.getStringExtra("text");
            taskListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("abode", "datachange1");

                    int checkcount = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        // UserTasks userTasks=data.getValue(UserTasks.class);
                        if (data.getKey().equals(uid)) {
                            check = false;
                            taskListRef.child(uid).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Log.d("abode", "datachange2");
                                    for (DataSnapshot data : snapshot.getChildren()) {
                                        if (data.getKey().equals("task: " + addedText)) {
                                            check = true;
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            if (check == false)
                                taskListRef.child(uid).child("task: " + addedText).setValue(new Task(isChecked, addedText));
                            checkcount++;
                            break;
                        }
                    }

                    if (checkcount == 0) {
                        taskListRef.child(uid).child("task: " + addedText).setValue(new Task(isChecked, addedText));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        Log.d("abode", "before getalltasks");

        getAllTasks();
        taskAdapter = new TaskAdapter(Check_List.this, 0, taskArrayList);
        listView.setAdapter(taskAdapter);
    }

    private void getAllTasks() {
        Log.d("abode", "datachange3");
        taskListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("abode", "datachange4");
                taskArrayList = new ArrayList<Task>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (data.getKey().equals(uid)) {
                        taskListRef.child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    taskArrayList.add(d.getValue(Task.class));
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d("abode", "after getalltasks");
                        break;

                    }
                }
                taskAdapter = new TaskAdapter(Check_List.this, 0, taskArrayList);
                listView.setAdapter(taskAdapter);
                Log.d("abode", "after linking the listview with the adapter");
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
