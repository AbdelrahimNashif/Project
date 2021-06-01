package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout;
    private EditText emailet, passwordet;
    private Button loginbtn;
    private TextView tvlogin, registertv, doYou, reset;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        linearLayout = findViewById(R.id.loginLinear);
        tvlogin = findViewById(R.id.tvlogin);
        emailet = findViewById(R.id.etemail);
        passwordet = findViewById(R.id.etpassword);
        loginbtn = findViewById(R.id.btnlogin);
        loginbtn.setOnClickListener(this);
        registertv = findViewById(R.id.registertv);
        registertv.setOnClickListener(this);
        doYou = findViewById(R.id.doYouHaveAnAcount);
        reset = findViewById(R.id.mainactivityrestpass);
        reset.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if (view == loginbtn) {
            if (emailet.getText().toString().equals("a") && passwordet.getText().toString().equals("a")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else
                Login(emailet.getText().toString(), passwordet.getText().toString());
        } else if (view == registertv) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (view == reset) {
            if (emailet.getText().toString().equals(null))
                Toast.makeText(this, "insert an email", Toast.LENGTH_LONG).show();
            else
                mAuth.sendPasswordResetEmail(emailet.getText().toString());
        }

    }

    public void Login(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Authentication successed.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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