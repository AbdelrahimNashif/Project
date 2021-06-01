package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class startActivity extends AppCompatActivity {
    TextView textView;
    Animation fade_in_anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        textView = findViewById(R.id.activity_start_TV);

        fade_in_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        textView.startAnimation(fade_in_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(startActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }
}