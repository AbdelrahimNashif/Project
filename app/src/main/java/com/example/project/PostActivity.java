package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class    PostActivity extends AppCompatActivity {
  //  private LinearLayout linearLayout;
    private TextView title,subtitle,text;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    //    linearLayout=findViewById(R.id.postactivitylinear);
        imageView=findViewById(R.id.postactivityimageview);
        title=findViewById(R.id.postactivitytitle);
        subtitle=findViewById(R.id.postactivitysubtitle);
        text=findViewById(R.id.postactivitytext);
        Intent intent=getIntent();
        Startup startup=(Startup)intent.getSerializableExtra("startup");
        imageView.setImageBitmap(MainActivity.StringToBitMap(startup.getImage()));
        title.setText(startup.getTitle());
        subtitle.setText(startup.getSubtitle());
        text.setText(startup.getText());

        


    }
}