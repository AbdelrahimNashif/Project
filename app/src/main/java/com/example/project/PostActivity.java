package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class    PostActivity extends AppCompatActivity {
  //  private LinearLayout linearLayout;
    private TextView title,subtitle,text;
    private ImageView imageView;
    //accout variables
    private CircleImageView accountCircleImageView;
    private TextView accountName,accountEmail,accountcountry, accountgender;

    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference("users");




    //    linearLayout=findViewById(R.id.postactivitylinear);
        imageView=findViewById(R.id.postactivityimageview);
        title=findViewById(R.id.postactivitytitle);
        subtitle=findViewById(R.id.postactivitysubtitle);
        text=findViewById(R.id.postactivitytext);

        //account
        accountCircleImageView=findViewById(R.id.postactivity_accountimage);
        accountName=findViewById(R.id.postactivity_nameTv);
        accountEmail=findViewById(R.id.postActivity_emailTv);
        accountcountry=findViewById(R.id.postactivity_countryTv);
        accountgender=findViewById(R.id.postactivity_genderTv);

        Intent intent=getIntent();
        Startup startup=(Startup)intent.getSerializableExtra("startup");
        imageView.setImageBitmap(MainActivity.StringToBitMap(startup.getImage()));
        title.setText(startup.getTitle());
        subtitle.setText(startup.getSubtitle());
        text.setText(startup.getText());
        uid=startup.getUid().toString();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    User user=ds.getValue(User.class);
                    if(user.getUid()!=null)

                        if(user.getUid().equals(uid)){
                            accountName.setText(user.getName());
                            accountEmail.setText(user.getEmail());
                            accountgender.setText(user.getGender());
                            accountcountry.setText(user.getCountry());
                            break;
                        }

                           /*
                            try {
                                accountEmail.setText(mAuth.getUser(uid).getEmail());
                            } catch (FirebaseAuthException e) {
                                e.printStackTrace();
                            }

                            );

                            */

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

}