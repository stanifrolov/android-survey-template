package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        TextView t = (TextView) findViewById(R.id.textResult);

        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        switch (score) {
            case 1:
                t.setText("Score is 1/5");
                break;
            case 2:
                t.setText("Score is 2/5");
                break;
            case 3:
                t.setText("Score is 3/5");
                break;
            case 4:
                t.setText("Score is 4/5");
                break;
            case 5:
                t.setText("Score is 5/5");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.menu_database:
                Intent databaseManager = new Intent(ResultActivity.this, AndroidDatabaseManager.class);
                startActivity(databaseManager);
                return true;
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }
}
