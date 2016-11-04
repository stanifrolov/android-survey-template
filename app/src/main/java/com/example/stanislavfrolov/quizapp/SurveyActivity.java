package com.example.stanislavfrolov.quizapp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

}
