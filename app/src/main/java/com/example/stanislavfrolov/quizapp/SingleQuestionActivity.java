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

    UserDatabaseHelper userDatabaseHelper;
    QuestionDatabaseHelper questionDatabaseHelper;
    List<Question> allQuestions;
    Question question;
    int questionID;
    String timestamp;
    TextView questionText;
    RadioButton radioButtonA, radioButtonB, radioButtonC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        userDatabaseHelper = new UserDatabaseHelper(this);
        questionDatabaseHelper = new QuestionDatabaseHelper(this);

        allQuestions = questionDatabaseHelper.getAllQuestions();
        Bundle bundle = getIntent().getExtras();
        questionID = bundle.getInt("questionID");
        question = allQuestions.get(questionID);
        timestamp = bundle.getString("timestamp");

        questionText = (TextView) findViewById(R.id.textQuestion);
        radioButtonA = (RadioButton) findViewById(R.id.optionA);
        radioButtonB = (RadioButton) findViewById(R.id.optionB);
        radioButtonC = (RadioButton) findViewById(R.id.optionC);

        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(this);
        setQuestionView();
    }

    @Override
    public void onClick(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.optionsGroup);
        RadioButton answer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        if (timestamp == null || timestamp.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            Timestamp newTimestamp = new Timestamp(calendar.getTime().getTime());
            timestamp = newTimestamp.toString();
        }
        userDatabaseHelper.addAnswer(question.getQuestion(), answer.getText().toString(), timestamp);

        Intent intent = new Intent(SingleQuestionActivity.this, ThankYouActivity.class);
        startActivity(intent);
        finish();
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

    private void setQuestionView() {
        questionText.setText(question.getQuestion());
        radioButtonA.setText(question.getOptionA());
        radioButtonB.setText(question.getOptionB());
        radioButtonC.setText(question.getOptionC());
    }
}
