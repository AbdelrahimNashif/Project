package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    Context context;
    ArrayList<Task> objects;


    public TaskAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.task_layout, parent, false);
        CheckBox checkBox =(CheckBox) view.findViewById(R.id.task_layout_checkBox);

        TextView text = (TextView) view.findViewById(R.id.task_layout_TextView);
        Task temp = objects.get(position);
        checkBox.setChecked(temp.isChecked());
        text.setText(temp.getText());




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
}
