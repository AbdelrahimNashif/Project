package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    private LinearLayout linearLayout;
    private SharedPreferences sp;
    private FloatingActionButton fab;
    private TextView maintv;
    private Button sbtn,fbtn,abtn;
    private String selectedType="a";
    //listView
    private ListView listView;//display
    private ArrayList<Startup> startupsList;//DATA
    private StartupAdapter arrayAdapter;//Adapter

    private Dialog d,d1;
    private int themeId = R.drawable.ic_baseline_wb_sunny_24;
    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference postRef;
    private FirebaseAuth auth;

    //dialog content
    private EditText title, subtitle, text;
    private Switch aSwitch;
    private String type="s";
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
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        postRef = firebaseDatabase.getReference("posts");

        sp=getSharedPreferences("mySP",0);


        linearLayout = findViewById(R.id.mainLinear);
        maintv = findViewById(R.id.maintv);
        sbtn=findViewById(R.id.mainactivitySbtn);
        fbtn=findViewById(R.id.mainactivityfbtn);
        abtn=findViewById(R.id.mainactivityabtn);
        sbtn.setOnClickListener(this);
        fbtn.setOnClickListener(this);
        abtn.setOnClickListener(this);
        fab = findViewById(R.id.fabbtn);
        fab.setOnClickListener(this);
        listItemTitle = findViewById(R.id.startuptitle);
        listItemSubtitle = findViewById(R.id.startupsubtitle);

        listView = findViewById(R.id.mainListView);
        listView.setOnItemClickListener(this);
        startupsList = new ArrayList<Startup>();

       Log.d("abdilrahim","1");


        Log.d("abdilrahim","2");

       // StartupsList.add(new Startup("b", "bb", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background)), "bbbbbb", "bbb", "bb"));
        //StartupsList.add(new Startup("b", "bb", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background)), "bbbbbb", "bbb", "bb"));
        arrayAdapter = new StartupAdapter(MainActivity.this, 0, startupsList);
        getAllPosts();

        /*theme
        if(!sp.contains("themeId")){
            sp.edit().putInt("themeId",ic_baseline_brightness_2_24)
        }
            if (themeId == R.drawable.ic_baseline_wb_sunny_24) {
                item.setIcon(R.drawable.ic_baseline_brightness_2_24);
                themeId = R.drawable.ic_baseline_brightness_2_24;
                linearLayout.setBackgroundColor(getColor(R.color.darkBackground));
                maintv.setTextColor(getColor(R.color.darkColor));
            } else if (themeId == R.drawable.ic_baseline_brightness_2_24) {
                item.setIcon(R.drawable.ic_baseline_wb_sunny_24);
                themeId = R.drawable.ic_baseline_wb_sunny_24;
                linearLayout.setBackgroundColor(getColor(R.color.brightBackground));
                maintv.setTextColor(getColor(R.color.brightColor));
            }

         */
    }

    private void getAllPosts() {
        postRef = firebaseDatabase.getReference("posts");
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                startupsList=new ArrayList<Startup>();
              //  startupsList.add(new Startup("a", "a", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.sky1)), "aaaaaa", "aaa", "aa","s"));
              //  startupsList.add(new Startup("b", "bb", BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.sky1)), "bbbbbb", "bbb", "bb","s"));
                if (selectedType.equals("a"))
                for (DataSnapshot data : snapshot.getChildren()) {
                        Startup startup = data.getValue(Startup.class);
                        startupsList.add(startup);

                }
                else
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Startup startup = data.getValue(Startup.class);
                        if (startup.getType().equals(selectedType))
                        startupsList.add(startup);
                    }
                arrayAdapter = new StartupAdapter(MainActivity.this, 0, startupsList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                Intent signoutIntent = new Intent(this, LoginActivity.class);
                startActivity(signoutIntent);
                break;
            case R.id.LoginMenuaProfile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.LoginMenuTheme:
                if(sp.contains("themeId"))
                if (themeId == R.drawable.ic_baseline_wb_sunny_24) {
                    item.setIcon(R.drawable.ic_baseline_brightness_2_24);
                    themeId = R.drawable.ic_baseline_brightness_2_24;
                    linearLayout.setBackgroundColor(getColor(R.color.darkBackground));
                    maintv.setTextColor(getColor(R.color.darkColor));
                    ///listItemTitle.setTextColor(getColor(R.color.darkColor));
                  ///  listItemSubtitle.setTextColor(getColor(R.color.darkColor));
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
                    linearLayout.setBackgroundResource(R.drawable.backgroundtwo);
                    maintv.setTextColor(getColor(R.color.brightColor));
                 ///   listItemTitle.setTextColor(getColor(R.color.brightColor));
                 ///   listItemSubtitle.setTextColor(getColor(R.color.brightColor));
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
        if(view==fbtn) {
            selectedType = "f";
            getAllPosts();
        }
        else if (view==sbtn) {
            selectedType = "s";
            getAllPosts();
        }
        else if (view==abtn) {
            selectedType = "a";
            getAllPosts();
        }
        if (view == publishbtn) {


            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
            Startup startup;
            if(bitmap==null)
                Toast.makeText(this,"please choose a picture",Toast.LENGTH_LONG).show();
            else {
                startup = new Startup("", uid, BitMapToString(bitmap), title.getText().toString(), subtitle.getText().toString(), text.getText().toString(),type );

                postRef = firebaseDatabase.getReference("posts").push();
                startup.setKey(postRef.getKey());
                postRef.setValue(startup);

                // startupsList.clear();
               // arrayAdapter.notifyDataSetChanged();
                // Startup startupWithImage = startup;
                // startupWithImage.setImage(bitmap);
                //StartupsList.add(startup);


                Toast.makeText(this, "published", Toast.LENGTH_LONG).show();
                d.dismiss();
            }

        }

        else if (view == imagebtn) {
            d1 = new Dialog(this);
            d1.setContentView(R.layout.activity_edit_profile_image_dialog);
            d1.setCancelable(true);
            camerabtndialog = d1.findViewById(R.id.camerabtn_profile_dialog);
            camerabtndialog.setOnClickListener(this);
            gallerybtndialog = d1.findViewById(R.id.gallerybtn_profile_dialog);
            gallerybtndialog.setOnClickListener(this);
            d1.show();


        }
        else if (view == camerabtndialog) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }
        else if (view == gallerybtndialog) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
        else if (view == fab) {
            d = new Dialog(this);
            d.setContentView(R.layout.activity_post_dialog);

            d.setCancelable(true);
            title = d.findViewById(R.id.postdialogtitle);
            subtitle = d.findViewById(R.id.postdialogsubtitle);
            text = d.findViewById(R.id.postdialogtext);
            aSwitch=d.findViewById(R.id.postdialogswitch);
            imagebtn = d.findViewById(R.id.postdialoggallery);
            imagebtn.setOnClickListener(this);
            publishbtn = d.findViewById(R.id.postdialogpublishbtn);
            publishbtn.setOnClickListener(this);
            aSwitch.setOnCheckedChangeListener(this);
            d.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "profile image didn't change ", Toast.LENGTH_LONG).show();
                ;
            }
        }
        else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream=getContentResolver().openInputStream(data.getData());
                     bitmap=BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }            }
        }
        d1.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, PostActivity.class);
        Startup startup = startupsList.get(i);
        intent.putExtra("startup", startup);
        startActivity(intent);


    }


    public static String BitMapToString(Bitmap bitmap) {
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if(b)
                type="f";
            else
                type="s";

    }
}