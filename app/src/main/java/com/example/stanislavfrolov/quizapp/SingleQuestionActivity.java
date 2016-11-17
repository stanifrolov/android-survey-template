package com.example.stanislavfrolov.quizapp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SingleQuestionActivity extends Activity implements View.OnClickListener {

    AnswerDatabaseHelper answerDatabaseHelper;
    QuestionDatabaseHelper questionDatabaseHelper;

    List<Question> allQuestions;

    String timestamp;
    String questionText;

    Question question;

    TextView questionTextView;
    RadioButton radioButtonA, radioButtonB, radioButtonC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        answerDatabaseHelper = new AnswerDatabaseHelper(this);
        questionDatabaseHelper = new QuestionDatabaseHelper(this);

        allQuestions = questionDatabaseHelper.getAllQuestions();

        getExtrasFromBundle();

        for(Question q : allQuestions) {
            if(q.getQuestion().equals(questionText))
                question = q;
        }

        setupLayout();

    }

    private void getExtrasFromBundle() {
        Bundle bundle = getIntent().getExtras();

        setQuestion(bundle);
        setTimestamp(bundle);
    }

    private void setupLayout() {
        setupLayoutElements();
        setupView();
    }

    private void setupLayoutElements() {
        questionTextView = (TextView) findViewById(R.id.textQuestion);
        radioButtonA = (RadioButton) findViewById(R.id.optionA);
        radioButtonB = (RadioButton) findViewById(R.id.optionB);
        radioButtonC = (RadioButton) findViewById(R.id.optionC);

        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(this);
    }

    private void setupView() {
        questionTextView.setText(question.getQuestion());
        radioButtonA.setText(question.getOptionA());
        radioButtonB.setText(question.getOptionB());
        radioButtonC.setText(question.getOptionC());
    }

    @Override
    public void onClick(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.optionsGroup);
        RadioButton answer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        answerDatabaseHelper.addAnswer(question.getQuestion(), answer.getText().toString(), timestamp);

        Intent intent = new Intent(SingleQuestionActivity.this, ThankYouActivity.class);
        startActivity(intent);
        finish();
    }

    private void setQuestion(Bundle bundle) {
        questionText = bundle.getString("questionTextView");
    }

    private void setTimestamp(Bundle bundle) {
        timestamp = bundle.getString("timestamp");
        if (timestampIsNullOrEmpty()) {
            setNewTimestamp();
        }
    }

    private boolean timestampIsNullOrEmpty() {
        return timestamp == null || timestamp.isEmpty();
    }

    private void setNewTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Timestamp newTimestamp = new Timestamp(calendar.getTime().getTime());
        timestamp = newTimestamp.toString();
    }

}
