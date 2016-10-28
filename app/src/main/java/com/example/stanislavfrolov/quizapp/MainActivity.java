package com.example.stanislavfrolov.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takeSurvey = (Button) findViewById(R.id.take_survey);
        takeSurvey.setOnClickListener(this);

        Button manualInput = (Button) findViewById(R.id.manual_input);
        manualInput.setOnClickListener(this);

        Button changeAnswer = (Button) findViewById(R.id.change_answer);
        changeAnswer.setOnClickListener(this);

        Button showStatistic = (Button) findViewById(R.id.show_statistic);
        showStatistic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.take_survey:
                intent = new Intent(this, SurveyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.manual_input:
                // TODO: 28.10.2016 Add ManualInputActivity
//                intent = new Intent(this, ManualInputActivity.class);
//                startActivity(intent);
//                finish();
                break;
            case R.id.change_answer:
                // TODO: 28.10.2016 Add ChangeAnswerActivity
//                intent = new Intent(this, ChangeAnswerActivity.class);
//                startActivity(intent);
//                finish();
                break;
            case R.id.show_statistic:
                // TODO: 28.10.2016 Add ShowStatisticActivity
//                intent = new Intent(this, ShowStatisticActivity.class);
//                startActivity(intent);
//                finish();
                break;
        }
    }
}
