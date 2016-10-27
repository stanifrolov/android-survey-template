package com.example.stanislavfrolov.quizapp;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends Activity {
    List<Question> allQuestions;
    int score = 0;
    int questionID = 0;
    Question question;
    TextView textQuestion;
    RadioButton radioButtonA, radioButtonB, radioButtonC;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        allQuestions = databaseHelper.getAllQuestions();
        question = allQuestions.get(questionID);

        textQuestion = (TextView) findViewById(R.id.textView1);
        radioButtonA = (RadioButton) findViewById(R.id.radio0);
        radioButtonB = (RadioButton) findViewById(R.id.radio1);
        radioButtonC = (RadioButton) findViewById(R.id.radio2);
        nextButton = (Button) findViewById(R.id.button1);

        setQuestionView();

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
                RadioButton answer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                Log.d("your answer", question.getCorrectAnswer() + " " + answer.getText());
                if (question.getCorrectAnswer().equals(answer.getText())) {
                    score++;
                    Log.d("score", "your score" + score);
                }
                if (questionID < 1) {
                    question = allQuestions.get(questionID);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.menu_database:
                Intent db_manager = new Intent(QuizActivity.this, DatabaseManager.class);
                startActivity(db_manager);
                return true;
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    private void setQuestionView() {
        textQuestion.setText(question.getQuestion());
        radioButtonA.setText(question.getOptionA());
        radioButtonB.setText(question.getOptionB());
        radioButtonC.setText(question.getOptionC());
        questionID++;
    }
}
