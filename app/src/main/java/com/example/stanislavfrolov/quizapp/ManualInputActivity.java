package com.example.stanislavfrolov.quizapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ManualInputActivity extends ListActivity {

    QuestionDatabaseHelper questionDatabaseHelper;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_manual_input, R.id.label, getAllQuestionsAsStrings());

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        String questionID = getQuestionIdFromItem(item);

        Bundle bundle = new Bundle();
        putExtrasToBundle(bundle, questionID);

        Intent intent = new Intent(this, SingleQuestionActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    private void putExtrasToBundle(Bundle bundle, String questionID) {
        bundle.putInt("questionID", Integer.parseInt(questionID) - 1);
    }

    private String getQuestionIdFromItem(String item) {
        String[] parts = item.split(" ");
        return parts[0];
    }

    public String[] getAllQuestionsAsStrings() {
        questionDatabaseHelper = new QuestionDatabaseHelper(this);
        List<Question> allQuestions = questionDatabaseHelper.getAllQuestions();

        return convertQuestionsToStrings(allQuestions);
    }

    @NonNull
    private String[] convertQuestionsToStrings(List<Question> allQuestions) {
        String[] allQuestionsAsStrings = new String[allQuestions.size()];

        for (int i = 0; i < allQuestions.size(); i++) {
            Question question = allQuestions.get(i);
            allQuestionsAsStrings[i] = question.getQuestion();
        }

        return allQuestionsAsStrings;
    }
}
