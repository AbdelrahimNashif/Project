package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameet,emailet,passwordet;
    private Button registerbtn;
  //  private SharedPreferences sp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userRef=firebaseDatabase.getReference().child("users");

        //  getSupportActionBar().setTitle("Register");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
       // sp=getSharedPreferences("myprefs",MODE_PRIVATE);
        nameet=findViewById(R.id.edregistername);
        emailet=findViewById(R.id.etregisteremail);
        passwordet=findViewById(R.id.etregisterpassword);
        registerbtn=findViewById(R.id.registerregisterbtn);
        registerbtn.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if(view==registerbtn) {
           /* Intent intent = new Intent(this, LoginActivity.class);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("email",emailet.getText().toString());
            editor.putString("password",passwordet.getText().toString());
            editor.putString("name",nameet.getText().toString());
            editor.commit();
            startActivity(intent);

            */

        creatUser(emailet,passwordet);

        }

    }

    private void creatUser(final EditText emailet, EditText passwordet) {
        mAuth.createUserWithEmailAndPassword(emailet.getText().toString(), passwordet.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                            user=new User(uid,nameet.getText().toString(),emailet.getText().toString(),"","","");
                            userRef.child(uid).setValue(user);

                            Toast.makeText(RegisterActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("MY_FIREBASE", task.toString());
                            Log.d("MY_FIREBASE", task.getException().toString());



                        }

                        // ...
                    }
                });
    }
}