package com.example.stanislavfrolov.quizapp;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ChangeAnswerActivity extends ListActivity implements ChangeAnswerDialog.NoticeDialogListener{

    AnswerDatabaseHelper answerDatabaseHelper;
    private int clickedItemPosition;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        String[] allAnswers = getAllAnswersAsStrings();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_manual_input, R.id.label, allAnswers);
        setListAdapter(adapter);

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

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        showNoticeDialog();
        clickedItemPosition = position;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        String item = (String) getListAdapter().getItem(clickedItemPosition);
        String question = getQuestionFromItem(item);
        String timestamp = getTimestampFromItem(item);

        Bundle bundle = new Bundle();
        putExtrasToBundle(bundle, question, timestamp);

        Intent intent = new Intent(this, SingleQuestionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        answerDatabaseHelper = new AnswerDatabaseHelper(this);

        String item = (String) getListAdapter().getItem(clickedItemPosition);
        String timestamp = getTimestampFromItem(item);

        answerDatabaseHelper.removeAnswer(timestamp);

        finish();
        startActivity(getIntent());
    }
    private String getQuestionFromItem(String item) {
        String[] parts = item.split(" ");

        String question = "";
        for(int i = 2; i < parts.length - 1; i ++) {
            question = question + " " + parts[i];
        }
        return question.trim();
    }

    @NonNull
    private String getTimestampFromItem(String item) {
        String[] parts = item.split(" ");

        return parts[0] + " " + parts[1];
    }

    @NonNull
    private Bundle putExtrasToBundle(Bundle bundle, String question, String timestamp) {
        bundle.putString("questionTextView", question);
        bundle.putString("timestamp", timestamp);

        return bundle;
    }

    public void showNoticeDialog() {
        DialogFragment dialog = new ChangeAnswerDialog();
        dialog.show(getFragmentManager(), "ChangeAnswerDialog");
    }

}
