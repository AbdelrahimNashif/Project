package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditImageDialogActivity extends AppCompatActivity implements View.OnClickListener {
private Button camerabtn,gallerybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_image_dialog);
        camerabtn=findViewById(R.id.camerabtn_profile_dialog);
        camerabtn.setOnClickListener(this);
        gallerybtn=findViewById(R.id.gallerybtn_profile_dialog);
        gallerybtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}