package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private LinearLayout linearLayout;
    private FloatingActionButton fab;
    private TextView maintv;

    private ListView listView;//display
    private ArrayList<Startup> startupsList;//DATA
    private StartupAdapter arrayAdapter;//Adapter
    private Dialog d;
    private int themeId = R.drawable.ic_baseline_wb_sunny_24;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference postRef;
    private FirebaseAuth auth;

    //dialog content
    private EditText title, subtitle, text;
    private Button imagebtn, publishbtn;
    private Bitmap bitmap;
    //dialog2 content
    private Button camerabtndialog, gallerybtndialog;

    //startup_layout.xml
    private TextView listItemTitle, listItemSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        linearLayout = findViewById(R.id.mainLinear);
        maintv = findViewById(R.id.maintv);
        fab = findViewById(R.id.fabbtn);
        fab.setOnClickListener(this);
        listItemTitle = findViewById(R.id.startuptitle);
        listItemSubtitle = findViewById(R.id.startupsubtitle);

        listView = findViewById(R.id.mainListView);
        listView.setOnItemClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        startupsList = new ArrayList<Startup>();
        startupsList.add(new Startup("a", "a", BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground), "aaaaaa", "aaa", "aa"));
        startupsList.add(new Startup("b", "bb", BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground), "bbbbbb", "bbb", "bb"));
        startupsList.add(new Startup("b", "bb", BitmapFactory.decodeResource(getResources(), R.drawable.formulas), "bbbbbb", "bbb", "bb"));
        startupsList.add(new Startup("b", "bb", BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background), "bbbbbb", "bbb", "bb"));


        arrayAdapter = new StartupAdapter(this, R.layout.startup_layout, startupsList);
        listView.setAdapter(arrayAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.LoginMenusignout:
                Toast.makeText(this, "sign out", Toast.LENGTH_LONG).show();
                auth.signOut();
                Intent signoutIntent=new Intent(this,LoginActivity.class);
                startActivity(signoutIntent);
                break;
            case R.id.LoginMenuaProfile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.LoginMenuTheme:
                if (themeId == R.drawable.ic_baseline_wb_sunny_24) {
                    item.setIcon(R.drawable.ic_baseline_brightness_2_24);
                    themeId = R.drawable.ic_baseline_brightness_2_24;
                    linearLayout.setBackgroundColor(getColor(R.color.darkBackground));
                    maintv.setTextColor(getColor(R.color.darkColor));
                    listItemTitle.setTextColor(getColor(R.color.darkColor));
                    listItemSubtitle.setTextColor(getColor(R.color.darkColor));
                  /*  emailet.setHintTextColor(getColor(R.color.darkColor));
                    emailet.setTextColor(getColor(R.color.darkColor));
                    emailet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.darkColor)));
                    passwordet.setHintTextColor(getColor(R.color.darkColor));
                    passwordet.setTextColor(getColor(R.color.darkColor));
                    passwordet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.darkColor)));
                    doYou.setTextColor(getColor(R.color.darkColor));

                   */


                } else if (themeId == R.drawable.ic_baseline_brightness_2_24) {
                    item.setIcon(R.drawable.ic_baseline_wb_sunny_24);
                    themeId = R.drawable.ic_baseline_wb_sunny_24;
                    linearLayout.setBackgroundColor(getColor(R.color.brightBackground));
                    maintv.setTextColor(getColor(R.color.brightColor));
                    listItemTitle.setTextColor(getColor(R.color.brightColor));
                    listItemSubtitle.setTextColor(getColor(R.color.brightColor));
                   /* emailet.setTextColor(getColor(R.color.brightColor));
                    emailet.setHintTextColor(getColor(R.color.brightColor));
                    emailet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.brightColor)));
                    passwordet.setTextColor(getColor(R.color.brightColor));
                    passwordet.setHintTextColor(getColor(R.color.brightColor));
                    passwordet.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.brightColor)));
                    doYou.setTextColor(getColor(R.color.brightColor));

                    */

                }

                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view == publishbtn) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
            Startup startup = new Startup("", uid, bitmap, title.getText().toString(), subtitle.getText().toString(), text.getText().toString());
            postRef = firebaseDatabase.getReference("posts").push();
            startup.setKey(postRef.getKey());//?
            postRef.setValue(startup);

           // Startup startupWithImage = startup;
           // startupWithImage.setImage(bitmap);
            startupsList.add(startup);
            Toast.makeText(this, "published", Toast.LENGTH_LONG).show();
        }

        if (view == imagebtn) {
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
        } else if (view == fab) {
            d = new Dialog(this);
            d.setContentView(R.layout.activity_post_dialog);
            d.setCancelable(true);
            title = d.findViewById(R.id.postdialogtitle);
            subtitle = d.findViewById(R.id.postdialogsubtitle);
            text = d.findViewById(R.id.postdialogtext);
            imagebtn = d.findViewById(R.id.postdialoggallery);
            imagebtn.setOnClickListener(this);
            publishbtn = d.findViewById(R.id.postdialogpublishbtn);
            publishbtn.setOnClickListener(this);
            d.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        d.dismiss();
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "profile image didn't change ", Toast.LENGTH_LONG).show();
                ;
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, PostActivity.class);
        Startup startup = startupsList.get(i);
        intent.putExtra("startup", startup);
        startActivity(intent);


    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;

        }

    }
}