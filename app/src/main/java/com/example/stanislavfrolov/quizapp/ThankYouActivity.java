package com.example.stanislavfrolov.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThankYouActivity extends Activity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        setupLayout();
    }

    private void setupLayout() {
        TextView textView = (TextView) findViewById(R.id.textThankYou);
        textView.setText("Thank you!");

        Button done = (Button) findViewById(R.id.thank_you_done);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
