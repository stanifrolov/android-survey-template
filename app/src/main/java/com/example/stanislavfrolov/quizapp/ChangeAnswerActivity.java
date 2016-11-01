package com.example.stanislavfrolov.quizapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ChangeAnswerActivity extends ListActivity {

    AnswerDatabaseHelper answerDatabaseHelper;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_manual_input, R.id.label, getAllAnsweredQuestionsAsStringArray());
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        String[] parts = item.split(" ");
        String questionID = parts[2];
        String timestamp = parts[0] + " " + parts[1];
        Intent intent = new Intent(this, SingleQuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("questionID", Integer.parseInt(questionID) - 1);
        bundle.putString("timestamp", timestamp);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public String[] getAllAnsweredQuestionsAsStringArray() {
        answerDatabaseHelper = new AnswerDatabaseHelper(this);
        List<Answer> allAnswers;
        allAnswers = answerDatabaseHelper.getAllAnsweredQuestions();
        String[] allQuestionsAsStrings = new String[allAnswers.size()];
        Answer answer;
        for (int i = 0; i < allAnswers.size(); i++) {
            answer = allAnswers.get(i);
            allQuestionsAsStrings[i] = answer.getTimestamp() + " " + answer.getQuestion() + " " + answer.getAnswer();
        }
        return allQuestionsAsStrings;
    }
}
