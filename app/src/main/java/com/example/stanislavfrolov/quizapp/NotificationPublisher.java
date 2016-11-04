package com.example.stanislavfrolov.quizapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, SurveyActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("ticker")
                .setContentTitle(NOTIFICATION_ID)
                .setContentText(NOTIFICATION)
                .setContentIntent(contentIntent)
                .setAutoCancel(true).build();

        // TODO: 04.11.2016  How to use this one
        //Notification notification = intent.getParcelableExtra(NOTIFICATION);
        //

        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

    }

}