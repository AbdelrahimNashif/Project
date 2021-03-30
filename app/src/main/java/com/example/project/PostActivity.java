package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    //  private LinearLayout linearLayout;
    private TextView title, subtitle, text;
    private ImageView imageView;
    //accout variables
    private CircleImageView accountCircleImageView;
    private TextView accountName, accountEmail, accountcountry, accountgender;

    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String uid;

    //top bar for favorate and add to my list
    private ImageView favorateImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");


        //top bar for favorate and add to my list
        favorateImageView = findViewById(R.id.activity_post_favorate_imageview);
        favorateImageView.setOnClickListener(this);


        //    linearLayout=findViewById(R.id.postactivitylinear);
        imageView = findViewById(R.id.postactivityimageview);
        title = findViewById(R.id.postactivitytitle);
        subtitle = findViewById(R.id.postactivitysubtitle);
        text = findViewById(R.id.postactivitytext);

        //account
        accountCircleImageView = findViewById(R.id.postactivity_accountimage);
        accountName = findViewById(R.id.postactivity_nameTv);
        accountEmail = findViewById(R.id.postActivity_emailTv);
        accountcountry = findViewById(R.id.postactivity_countryTv);
        accountgender = findViewById(R.id.postactivity_genderTv);
        accountEmail.setOnClickListener(this);

        //intent
        Intent intent = getIntent();
        Startup startup = (Startup) intent.getSerializableExtra("startup");
        imageView.setImageBitmap(MainActivity.StringToBitMap(startup.getImage()));
        title.setText(startup.getTitle());
        subtitle.setText(startup.getSubtitle());
        text.setText(startup.getText());
        uid = startup.getUid().toString();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.getUid() != null)

                        if (user.getUid().equals(uid)) {
                            accountName.setText(user.getName());
                            accountEmail.setText(user.getEmail());
                            accountgender.setText(user.getGender());
                            accountcountry.setText(user.getCountry());
                            accountCircleImageView.setImageBitmap(StringToBitMap(user.getImage()));
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

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;

        }

    }

    @Override
    public void onClick(View view) {
        if (view == accountEmail) {
            String[] emails = {accountEmail.getText().toString()};
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            intent2.setType("message/rfc822");
            intent2.putExtra(Intent.EXTRA_EMAIL, emails);
            intent2.putExtra(Intent.EXTRA_SUBJECT, "hello " + accountName.getText().toString() + " a user from lets startup wants to communicate with you!");
            intent2.putExtra(Intent.EXTRA_TEXT, "the user came from your post: " + title.getText().toString() + "\n");
            startActivity(Intent.createChooser(intent2, "Send Email"));
        } else if (view == favorateImageView) {
            Log.d("abode","on click");
            if (favorateImageView.equals(R.drawable.ic_baseline_favorite_border_24)) {
                favorateImageView.setImageResource(R.drawable.ic_baseline_favorite_24);
                Log.d("abode", "in the if");
            }
        } else {
            favorateImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            Log.d("abode", "in the else");
        }
    }
}