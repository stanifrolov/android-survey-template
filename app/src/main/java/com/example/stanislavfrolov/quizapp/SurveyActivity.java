package com.example.stanislavfrolov.quizapp;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SurveyActivity extends Activity {
    DatabaseHelper databaseHelper;
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
        setContentView(R.layout.activity_survey);

        databaseHelper = new DatabaseHelper(this);
        allQuestions = databaseHelper.getAllQuestions();
        question = allQuestions.get(questionID);

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        radioButtonA = (RadioButton) findViewById(R.id.optionA);
        radioButtonB = (RadioButton) findViewById(R.id.optionB);
        radioButtonC = (RadioButton) findViewById(R.id.optionC);
        nextButton = (Button) findViewById(R.id.button1);

        setQuestionView();

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
                RadioButton answer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if (questionID < allQuestions.size()) {
                    question = allQuestions.get(questionID);
                    question.setAnswer(answer.getText().toString());
                    databaseHelper.addAnswerToQuestion(question);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(SurveyActivity.this, ThankYouActivity.class);
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
                Intent db_manager = new Intent(SurveyActivity.this, DatabaseManagerActivity.class);
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
