package com.example.stanislavfrolov.quizapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLayout();
    }

    private void setupLayout() {
        Button takeSurvey = (Button) findViewById(R.id.take_survey);
        takeSurvey.setOnClickListener(this);

        Button manualInput = (Button) findViewById(R.id.manual_input);
        manualInput.setOnClickListener(this);

        Button changeAnswer = (Button) findViewById(R.id.change_answer);
        changeAnswer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.take_survey:
                scheduleNotification("1 second delay", 1000);
                intent = new Intent(this, SurveyActivity.class);
                startActivity(intent);
                onDestroy();
                break;
            case R.id.manual_input:
                intent = new Intent(this, ManualInputActivity.class);
                startActivity(intent);
                onDestroy();
                break;
            case R.id.change_answer:
                intent = new Intent(this, ChangeAnswerActivity.class);
                startActivity(intent);
                onDestroy();
                break;
        }
    }

    private void scheduleNotification(String content, int delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);

        Notification notification = buildNotification(content);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        setAlarm(delay, notificationIntent);
    }

    private void setAlarm(int delay, Intent notificationIntent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification buildNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle("Please take the survey!");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        Intent intent = new Intent(this, SurveyActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}
