package com.example.project;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyIntentService extends IntentService {
    public MyIntentService()
    {
        super("MyIntentService");

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //phase 1
        int icon = android.R.drawable.star_on;
        long when = System.currentTimeMillis();
        String title = "title";
        String text="My text";

        //phase 2
        Intent intent1 = new Intent(this, ProfileActivity.class);
        intent1.putExtra("key", "Uzi oranim");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "M_CH_ID");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        //phase 3
        Notification notification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(icon).setWhen(when)
                .setAutoCancel(true).setContentTitle(title)
                .setContentText(text).build();
        notificationManager.notify(1, notification);

    }
}
