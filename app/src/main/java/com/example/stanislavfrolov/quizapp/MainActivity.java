package com.example.stanislavfrolov.quizapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLayout();
    }

    private void setupLayout() {
        Button takeSurvey = (Button) findViewById(R.id.take_survey);
        takeSurvey.setOnClickListener(this);

        Button manualInput = (Button) findViewById(R.id.manual_input);
        manualInput.setOnClickListener(this);

        Button changeAnswer = (Button) findViewById(R.id.change_answer);
        changeAnswer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.take_survey:
                intent = new Intent(this, SurveyActivity.class);
                startActivity(intent);
                onDestroy();
                break;
            case R.id.manual_input:
                intent = new Intent(this, ManualInputActivity.class);
                startActivity(intent);
                onDestroy();
                break;
            case R.id.change_answer:
                intent = new Intent(this, ChangeAnswerActivity.class);
                startActivity(intent);
                onDestroy();
                break;
        }
    }

    @Override
    public  void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
