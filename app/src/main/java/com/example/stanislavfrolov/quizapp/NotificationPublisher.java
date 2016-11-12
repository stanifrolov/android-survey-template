package com.example.stanislavfrolov.quizapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getNotificationId(intent), notification);
    }

    private int getNotificationId(Intent intent) {
        return intent.getIntExtra(NOTIFICATION_ID, 0);
    }

}