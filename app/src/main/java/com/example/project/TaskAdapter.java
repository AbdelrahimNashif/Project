package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    Context context;
    ArrayList<Task> objects;

    private CheckBox checkBox;
    private TextView text;
    private Boolean check;
    private int pos;


    //firebase
    private DatabaseReference taskListRef;
    private String uid;

    public TaskAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //firebase
        taskListRef = FirebaseDatabase.getInstance().getReference().child("taskLists");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.task_layout, parent, false);
        checkBox = (CheckBox) view.findViewById(R.id.task_layout_checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                check = b;
                pos = position;
                Log.d("abode", "checkvbox changed to " + String.valueOf(check));
                Log.d("abode", "the text is:  " + objects.get(pos).getText());

                String[] sentences = objects.get(pos).getText().split("\n");
                String untilDot = sentences[0];
                taskListRef.child(uid).child("task: " + untilDot).child("checked").setValue(String.valueOf(check));

            }
        });


        text = (TextView) view.findViewById(R.id.task_layout_TextView);
        Task temp = objects.get(position);
        if (temp.isChecked().equals("true"))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);
        text.setText(temp.getText());


        //  Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        //  view.startAnimation(animation);
        return view;

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
/*
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        check = b;
        Log.d("abode", "checkvbox changed to " + String.valueOf(check));
        Log.d("abode", "the text is:  " + text.getText().toString());
        taskListRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Task task = data.getValue(Task.class);
                    String[] sentences = objects.get(pos).getText().split("\n");
                    String untilDot = sentences[0];
                    if (task.getText().equals(objects.get(pos).getText()))
                        taskListRef.child(uid).child("task: " + untilDot).child("checked").setValue(String.valueOf(check));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


}
 */
}
