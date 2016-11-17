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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class SurveyActivity extends Activity implements View.OnClickListener {

    AnswerDatabaseHelper answerDatabaseHelper;
    QuestionDatabaseHelper questionDatabaseHelper;

    List<Question> allQuestions;
    Question question;
    int questionID = 0;

    TextView questionText;
    RadioButton radioButtonA, radioButtonB, radioButtonC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        answerDatabaseHelper = new AnswerDatabaseHelper(this);
        questionDatabaseHelper = new QuestionDatabaseHelper(this);

        allQuestions = questionDatabaseHelper.getAllQuestions();
        question = allQuestions.get(questionID);

        setupLayout();
    }

    private void setupLayout() {
        setupLayoutElements();
        setupView();
    }

    private void setupLayoutElements() {
        questionText = (TextView) findViewById(R.id.textQuestion);
        radioButtonA = (RadioButton) findViewById(R.id.optionA);
        radioButtonB = (RadioButton) findViewById(R.id.optionB);
        radioButtonC = (RadioButton) findViewById(R.id.optionC);

        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(this);
    }

    private void setupView() {
        questionText.setText(question.getQuestion());
        radioButtonA.setText(question.getOptionA());
        radioButtonB.setText(question.getOptionB());
        radioButtonC.setText(question.getOptionC());
    }

    @Override
    public void onClick(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.optionsGroup);
        RadioButton answer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        answerDatabaseHelper.addAnswer(question.getQuestion(), answer.getText().toString(), getNewTimeStamp());

        questionID++;
        if (surveyNotFinished()) {
            question = allQuestions.get(questionID);
            setupView();
        } else {
            Intent intent = new Intent(this, ThankYouActivity.class);
            startActivity(intent);
            scheduleNotification("Last survey was 1 hours ago", 1);
            finish();
        }
    }

    private String getNewTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        Timestamp newTimestamp = new Timestamp(calendar.getTime().getTime());

        return newTimestamp.toString();
    }

    private boolean surveyNotFinished() {
        return questionID < allQuestions.size();
    }

    private void scheduleNotification(String content, int delayInHours) {
        int delayInMillis = getDelayInMilliSeconds(delayInHours);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);

        Notification notification = buildNotification(content);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        setAlarm(delayInMillis, notificationIntent);
    }

    private int getDelayInMilliSeconds(int delayInHours) {
        return (delayInHours * 60 * 60 * 1000);
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

    private void setAlarm(int delay, Intent notificationIntent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @Override
    public void onBackPressed() {
    }

}
