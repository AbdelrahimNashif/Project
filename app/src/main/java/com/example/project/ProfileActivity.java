package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView profileimage;
    private TextView nametv, emailtv,gendertv,countrytv;
    private Dialog d;
    private SharedPreferences sp;
    private Button camerabtndialog, gallerybtndialog;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String uid;
    //edit profile dialog
    private EditText dialogName, dialogCountry;
    private RadioButton dialogMaleRBtn, dialogFemaleRBtn;
    private Button dialogEditBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference("users");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        sp = getSharedPreferences("myprefs", MODE_PRIVATE);
        profileimage = findViewById(R.id.profileimage_profileactivity);
        nametv = findViewById(R.id.nametv_profileactivity);
        emailtv = findViewById(R.id.emailtv_profileactivity);
        gendertv=findViewById(R.id.gendertv_profileactivity);
        countrytv=findViewById(R.id.countrytv_profileactivity);




        emailtv.setText(mAuth.getCurrentUser().getEmail().toString());


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    User user=ds.getValue(User.class);
                    if(user.getUid()!=null)
                        if(user.getUid().equals(uid)){
                            if(user.getName()!="")
                                nametv.setText(user.getName());
                            if(user.getGender()!="")
                                gendertv.setText(user.getGender());
                            if(user.getCountry()!="")
                                countrytv.setText(user.getCountry());

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == camerabtndialog) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }
          else if (view == gallerybtndialog) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
          else if (view == dialogEditBtn){
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        User user=ds.getValue(User.class);
                        if(user.getUid()!=null)
                            if(user.getUid().equals(uid)){
                                user.setName(dialogName.getText().toString());
                                if(dialogMaleRBtn.isChecked())
                                user.setGender("male");
                                else if(dialogFemaleRBtn.isChecked())
                                    user.setGender("female");
                                user.setCountry(dialogCountry.getText().toString());

                                userRef.child(uid).child("name").setValue(user.getName());
                                userRef.child(uid).child("gender").setValue(user.getGender());
                                userRef.child(uid).child("country").setValue(user.getCountry());
                                d.dismiss();
                                break;
                            }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        d.dismiss();
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                profileimage.setImageBitmap(bitmap);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "profile image didn't change ", Toast.LENGTH_LONG).show();
                ;
            }
        }
        else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream=getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                    profileimage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profilemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profilemenu_editProfile:
                d = new Dialog(this);
                d.setContentView(R.layout.profileactivity_editprofile_dialog);
                d.setCancelable(true);
                dialogName = d.findViewById(R.id.profileActivity_editProfile_dialog_nameET);
                dialogMaleRBtn = d.findViewById(R.id.profileActivity_editProfile_dialog_maleRadiobtn);
                dialogFemaleRBtn = d.findViewById(R.id.profileActivity_editProfile_dialog_femaleRadiobtn);
                dialogCountry=d.findViewById(R.id.profileActivity_editProfile_dialog_countryET);
                dialogEditBtn=d.findViewById(R.id.profileActivity_editProfile_dialog_editbtn);
                dialogEditBtn.setOnClickListener(this);
                d.show();
                break;
            case R.id.profilemenu_editprofileimage:
                d = new Dialog(this);
                d.setContentView(R.layout.activity_edit_profile_image_dialog);
                d.setCancelable(true);
                camerabtndialog = d.findViewById(R.id.camerabtn_profile_dialog);
                camerabtndialog.setOnClickListener(this);
                gallerybtndialog = d.findViewById(R.id.gallerybtn_profile_dialog);
                gallerybtndialog.setOnClickListener(this);
                d.show();
                break;

            case R.id.profilemenu_resetpassword:
                mAuth.sendPasswordResetEmail(emailtv.getText().toString());
                Toast.makeText(this,"check your email to reset password", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


}