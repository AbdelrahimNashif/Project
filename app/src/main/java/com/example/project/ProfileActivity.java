package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView profileimage;
    private TextView nametv, emailtv, passwordtv;
    private Button iditimagebtn;
    private Dialog d;
    private SharedPreferences sp;
    private Button camerabtndialog, gallerybtndialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        sp = getSharedPreferences("myprefs", MODE_PRIVATE);
        profileimage = findViewById(R.id.profileimage_profileactivity);
        nametv = findViewById(R.id.nametv_profileactivity);
        emailtv = findViewById(R.id.emailtv_profileactivity);
        passwordtv = findViewById(R.id.passwordtv_profileactivity);
        iditimagebtn = findViewById(R.id.iditimagebtn_profileactivity);
        iditimagebtn.setOnClickListener(this);
        nametv.setText(sp.getString("name", ""));
        emailtv.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        passwordtv.setText(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
    }

    @Override
    public void onClick(View view) {
        if (view == iditimagebtn) {
            d = new Dialog(this);
            d.setContentView(R.layout.activity_edit_profile_image_dialog);
            d.setCancelable(true);
            camerabtndialog = d.findViewById(R.id.camerabtn_profile_dialog);
            camerabtndialog.setOnClickListener(this);
            gallerybtndialog = d.findViewById(R.id.gallerybtn_profile_dialog);
            gallerybtndialog.setOnClickListener(this);
            d.show();


        } else if (view == camerabtndialog) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        } else if (view == gallerybtndialog) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
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
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                //  Bitmap bitmap=(Bitmap) data.getExtras().get("data");
                //  profileimage.setImageBitmap(bitmap);
                profileimage.setImageBitmap(bitmap);
            }
        }
    }
}