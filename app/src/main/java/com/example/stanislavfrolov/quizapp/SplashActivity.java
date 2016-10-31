package com.example.stanislavfrolov.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager();
        Intent intent;
        if (sessionManager.userIsLoggedIn()) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
