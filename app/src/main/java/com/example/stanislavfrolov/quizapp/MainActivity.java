package com.example.stanislavfrolov.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
                intent = new Intent(this, ManualInputActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.change_answer:
                intent = new Intent(this, ChangeAnswerActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.show_statistic:
                intent = new Intent(this, ShowStatisticActivity.class);
                startActivity(intent);
                finish();
                break;
        }
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
