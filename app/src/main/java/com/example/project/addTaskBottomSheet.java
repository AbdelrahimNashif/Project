package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class addTaskBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    //firebase
    private DatabaseReference postRef;
    private FirebaseDatabase firebaseDatabase;
    private String uid;

    //fragment stuff
    private EditText editText;
    private CheckBox checkBox;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_task_layout, container, false);
        //firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        editText = view.findViewById(R.id.add_task_layout_editText);
        checkBox = view.findViewById(R.id.add_task_layout_checkbox);
        fab=view.findViewById(R.id.add_task_layout_fab);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view==fab){
            //UserTasks userTasks=new UserTasks(uid,)
            //postRef = firebaseDatabase.getReference("task lists").push();
        Intent intent= new Intent(getContext(),Check_List.class);
            Log.d("abode", String.valueOf(checkBox.isChecked()));
        intent.putExtra("isChecked", String.valueOf(checkBox.isChecked()));
        intent.putExtra("text",editText.getText().toString());
        startActivity(intent);

        }
    }
}
