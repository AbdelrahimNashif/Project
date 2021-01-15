package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class    PostActivity extends AppCompatActivity {
    TextView title,subtitle,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        title=findViewById(R.id.postactivitytitle);
        subtitle=findViewById(R.id.postactivitysubtitle);
        text=findViewById(R.id.postactivitytext);
        Intent intent=getIntent();
        Startup startup=(Startup)intent.getSerializableExtra("startup");
        title.setText(startup.getTitle());
        subtitle.setText(startup.getSubtitle());
        text.setText(startup.getText());

    }
}