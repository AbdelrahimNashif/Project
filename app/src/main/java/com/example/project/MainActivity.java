package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;//display
    private ArrayList<Startup> startupsList;//DATA
    private StartupAdapter arrayAdapter;//Adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startupsList=new ArrayList<Startup>();
        startupsList.add(new Startup(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background), "aaaaaa", "aaa", "aa"));
        startupsList.add(new Startup(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background), "bbbbbb", "bbb", "bb"));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loginmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuProfile:
                Toast.makeText(this, "Profile Menu item", Toast.LENGTH_LONG).show();
                break;
            case R.id.LoginMenuSettings:
                Toast.makeText(this, "settings Menu", Toast.LENGTH_LONG).show();
                break;

            case R.id.LoginMenusign_in:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.LoginMenuaProfile:
                Intent intent2 = new Intent(this, ProfileActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

}