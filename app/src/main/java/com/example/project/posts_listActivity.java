package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class posts_listActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView title;
    //listView
    private ListView listView;//display
    private ArrayList<Startup> startupsList;//DATA
    private StartupAdapter arrayAdapter;//Adapter

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference postRef;
    private FirebaseAuth auth;
    private String uid;

    private String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);
        title=findViewById(R.id.activity_posts_list_titletv);

        listView = findViewById(R.id.mainListView);
        listView.setOnItemClickListener(this);
        startupsList = new ArrayList<Startup>();

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        postRef = firebaseDatabase.getReference("posts");
        uid = auth.getCurrentUser().getUid().toString();


        selectedType = "a";

        // StartupsList.add(new Startup("b", "bb", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background)), "bbbbbb", "bbb", "bb"));
        //StartupsList.add(new Startup("b", "bb", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background)), "bbbbbb", "bbb", "bb"));
        arrayAdapter = new StartupAdapter(posts_listActivity.this, 0, startupsList);
        getAllPosts();


    }

    private void getAllPosts() {
        Intent intent = getIntent();
        if (intent.getStringExtra("selectedType") != null)
            selectedType = intent.getStringExtra("selectedType");
        else if (intent.getStringExtra("type") != null)
            selectedType = intent.getStringExtra("type");

        //title adjustment
        if(selectedType.equals("s"))
            title.setText("startups");
        else if(selectedType.equals("f"))
            title.setText("financiers");
        else if(selectedType.equals("a"))
            title.setText("all posts");
        else
            title.setText("my posts");


        postRef = firebaseDatabase.getReference("posts");
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                startupsList = new ArrayList<Startup>();
                //  startupsList.add(new Startup("a", "a", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.sky1)), "aaaaaa", "aaa", "aa","s"));
                //  startupsList.add(new Startup("b", "bb", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.sky1)), "bbbbbb", "bbb", "bb","s"));
                if (selectedType.equals("a"))
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Startup startup = data.getValue(Startup.class);
                        startupsList.add(startup);

                    }
                else if (selectedType.equals("s") || selectedType.equals("f"))
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Startup startup = data.getValue(Startup.class);
                        if (startup.getType().equals(selectedType))
                            startupsList.add(startup);
                    }
                else {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Startup startup = data.getValue(Startup.class);
                        if (startup.getUid().equals(uid))
                            startupsList.add(startup);
                    }
                }
                arrayAdapter = new StartupAdapter(posts_listActivity.this, 0, startupsList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, PostActivity.class);
        Startup startup = startupsList.get(i);
        intent.putExtra("startup", startup);
        startActivity(intent);


    }

}
