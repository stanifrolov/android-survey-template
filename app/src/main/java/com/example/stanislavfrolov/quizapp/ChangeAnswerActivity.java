package com.example.stanislavfrolov.quizapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ChangeAnswerActivity extends ListActivity {

    AnswerDatabaseHelper answerDatabaseHelper;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_manual_input, R.id.label, getAllAnswersAsStrings());

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        String questionID = getQuestionIDFromItem(item);
        String timestamp = getTimestampFromItem(item);

        Bundle bundle = new Bundle();
        setExtrasToBundle(bundle, questionID, timestamp);

        Intent intent = new Intent(this, SingleQuestionActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    @NonNull
    private String getTimestampFromItem(String item) {
        String[] parts = item.split(" ");

        return parts[0] + " " + parts[1];
    }

    private String getQuestionIDFromItem(String item) {
        String[] parts = item.split(" ");

        return parts[2];
    }

    @NonNull
    private Bundle setExtrasToBundle(Bundle bundle, String questionID, String timestamp) {
        bundle.putInt("questionID", Integer.parseInt(questionID) - 1);
        bundle.putString("timestamp", timestamp);

        return bundle;
    }

    public String[] getAllAnswersAsStrings() {
        answerDatabaseHelper = new AnswerDatabaseHelper(this);
        List<Answer> allAnswers = answerDatabaseHelper.getAllAnsweredQuestions();

        return convertToStrings(allAnswers);
    }

    @NonNull
    private String[] convertToStrings(List<Answer> allAnswers) {
        String[] allAnswersAsStrings = new String[allAnswers.size()];

        for (int i = 0; i < allAnswers.size(); i++) {
            Answer answer = allAnswers.get(i);
            allAnswersAsStrings[i] = getAnswerAsString(answer);
        }

        return allAnswersAsStrings;
    }

    @NonNull
    private String getAnswerAsString(Answer answer) {
        return answer.getTimestamp() + " " + answer.getQuestion() + " " + answer.getAnswer();
    }
}
