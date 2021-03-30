package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StartupAdapter extends ArrayAdapter<Startup> {
    Context context;
    ArrayList<Startup> objects;

    public StartupAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.startup_layout, parent, false);


        TextView title = (TextView) view.findViewById(R.id.startuptitle);
        TextView subtitle = (TextView) view.findViewById(R.id.startupsubtitle);
        ImageView imageView = (ImageView) view.findViewById(R.id.startupImage);
        Startup temp = objects.get(position);

        title.setText(temp.getTitle());
        subtitle.setText(temp.getSubtitle());
        imageView.setImageBitmap( StringToBitMap(temp.getImage()));

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        view.startAnimation(animation);
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
