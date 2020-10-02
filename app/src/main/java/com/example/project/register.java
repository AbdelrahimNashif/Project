package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity implements View.OnClickListener {
    private EditText nameet,emailet,passwordet;
    private Button registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameet=findViewById(R.id.edregistername);
        emailet=findViewById(R.id.etregisteremail);
        passwordet=findViewById(R.id.etregisterpassword);
        registerbtn=findViewById(R.id.registerregisterbtn);
        registerbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view==registerbtn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}