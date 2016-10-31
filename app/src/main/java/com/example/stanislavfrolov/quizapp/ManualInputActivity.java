package com.example.stanislavfrolov.quizapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ManualInputActivity extends ListActivity {

    QuestionDatabaseHelper questionDatabaseHelper;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_manual_input, R.id.label, getAllQuestionsAsStringArray());
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        String[] parts = item.split(" ");
        String questionID = parts[0];
        Intent intent = new Intent(this, SingleQuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("questionID", Integer.parseInt(questionID) - 1);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public String[] getAllQuestionsAsStringArray() {
        questionDatabaseHelper = new QuestionDatabaseHelper(this);
        List<Question> allQuestions;
        allQuestions = questionDatabaseHelper.getAllQuestions();
        String[] allQuestionsAsStrings = new String[allQuestions.size()];
        Question question;
        for (int i = 0; i < allQuestions.size(); i++) {
            question = allQuestions.get(i);
            allQuestionsAsStrings[i] = question.getQuestion();
        }
        return allQuestionsAsStrings;
    }
}
