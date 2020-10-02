package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText emailet,passwordet;
    private Button loginbtn;
    private TextView registertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        emailet=findViewById(R.id.etemail);
        passwordet=findViewById(R.id.etpassword);
        loginbtn=findViewById(R.id.btnlogin);
        loginbtn.setOnClickListener(this);
        registertv=findViewById(R.id.registertv);
        registertv.setOnClickListener(this);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.MenuProfile:
                Toast.makeText(this, "Profile Menu item",Toast.LENGTH_LONG).show();
                break;
            case R.id.MenuSettings:
                Toast.makeText(this, "settings Menu",Toast.LENGTH_LONG).show();
                break;

            case R.id.Menusignout:
                Toast.makeText(this, "sign out Menu",Toast.LENGTH_LONG).show();
                break;
            case  R.id.acountlogomenu:
                Intent intent=new Intent(this,motion_test.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        if(view==loginbtn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
         else if(view==registertv) {
            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        }


    }


    }