package com.example.stanislavfrolov.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class SingleQuestionActivity extends Activity implements View.OnClickListener {

    AnswerDatabaseHelper answerDatabaseHelper;
    QuestionDatabaseHelper questionDatabaseHelper;

    List<Question> allQuestions;

    String timestamp;
    Question question;
    int questionID;

    TextView questionText;
    RadioButton radioButtonA, radioButtonB, radioButtonC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        answerDatabaseHelper = new AnswerDatabaseHelper(this);
        questionDatabaseHelper = new QuestionDatabaseHelper(this);

        allQuestions = questionDatabaseHelper.getAllQuestions();

        getExtrasFromBundle();

        question = allQuestions.get(questionID);

        getLayoutElements();

        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(this);

        setQuestionView();
    }

    private void getLayoutElements() {
        questionText = (TextView) findViewById(R.id.textQuestion);
        radioButtonA = (RadioButton) findViewById(R.id.optionA);
        radioButtonB = (RadioButton) findViewById(R.id.optionB);
        radioButtonC = (RadioButton) findViewById(R.id.optionC);
    }

    private void getExtrasFromBundle() {
        Bundle bundle = getIntent().getExtras();

        setQuestionId(bundle);
        setTimestamp(bundle);
    }

    private void setQuestionView() {
        questionText.setText(question.getQuestion());
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

    private void setQuestionId(Bundle bundle) {
        questionID = bundle.getInt("questionID");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

}
