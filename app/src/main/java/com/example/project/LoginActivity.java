package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout;
    private EditText emailet,passwordet;
    private Button loginbtn;
    private TextView tvlogin,registertv,doYou;
    private SharedPreferences sp;
    private int themeId= R.drawable.ic_baseline_wb_sunny_24;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        linearLayout=findViewById(R.id.loginLinear);
        tvlogin=findViewById(R.id.tvlogin);
        sp=getSharedPreferences("myprefs",MODE_PRIVATE);
        emailet=findViewById(R.id.etemail);
        passwordet=findViewById(R.id.etpassword);
        loginbtn=findViewById(R.id.btnlogin);
        loginbtn.setOnClickListener(this);
        registertv=findViewById(R.id.registertv);
        registertv.setOnClickListener(this);
        doYou=findViewById(R.id.doYouHaveAnAcount);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loginmenu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.MenuProfile:
                Toast.makeText(this, "Profile Menu item",Toast.LENGTH_LONG).show();
                break;
            case R.id.LoginMenuSettings:
                Toast.makeText(this, "settings Menu",Toast.LENGTH_LONG).show();
                break;

            case R.id.LoginMenusignout:
                Toast.makeText(this, "sign out Menu",Toast.LENGTH_LONG).show();
                break;
            case  R.id.LoginMenuaProfile:
                Intent intent=new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.LoginMenuTheme:
                if (themeId==R.drawable.ic_baseline_wb_sunny_24){
                item.setIcon(R.drawable.ic_baseline_brightness_2_24);
                themeId=R.drawable.ic_baseline_brightness_2_24;
                    linearLayout.setBackgroundColor(getColor(R.color.darkBackground));
                    tvlogin.setTextColor(getColor(R.color.darkColor));
                    emailet.setHintTextColor(getColor(R.color.darkColor));
                    emailet.setTextColor(getColor(R.color.darkColor));
                    emailet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.darkColor)));
                    passwordet.setHintTextColor(getColor(R.color.darkColor));
                    passwordet.setTextColor(getColor(R.color.darkColor));
                    passwordet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.darkColor)));
                    doYou.setTextColor(getColor(R.color.darkColor));


                }
                else if(themeId==R.drawable.ic_baseline_brightness_2_24){
                    item.setIcon(R.drawable.ic_baseline_wb_sunny_24);
                    themeId=R.drawable.ic_baseline_wb_sunny_24;
                    linearLayout.setBackgroundColor(getColor(R.color.brightBackground));
                    tvlogin.setTextColor(getColor(R.color.brightColor));
                    emailet.setTextColor(getColor(R.color.brightColor));
                    emailet.setHintTextColor(getColor(R.color.brightColor));
                    emailet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.brightColor)));
                    passwordet.setTextColor(getColor(R.color.brightColor));
                    passwordet.setHintTextColor(getColor(R.color.brightColor));
                    passwordet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.brightColor)));
                    doYou.setTextColor(getColor(R.color.brightColor));

                }

        break;
                }
        return true;
    }

    @Override
    public void onClick(View view) {

        if(view==loginbtn) {
               Login(emailet.getText().toString(),passwordet.getText().toString());
        }

         else if(view==registertv) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }


    }

    public void Login(final String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Authentication successed.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    }