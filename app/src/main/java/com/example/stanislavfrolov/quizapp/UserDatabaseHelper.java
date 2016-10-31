package com.example.stanislavfrolov.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.Calendar;

class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuizAppUser";
    private static final String TABLE_QUESTION = "quiz_user";

    private static final String KEY_TIMESTAMP = "Timestamp";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_ANSWER = "Answer";

    private SQLiteDatabase database;

    UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        database = sqlitedatabase;
        String createTable = "CREATE TABLE IF NOT EXISTS "
                + TABLE_QUESTION + " ( "
                + KEY_TIMESTAMP + " TEXT, "
                + KEY_QUESTION + " TEXT, "
                + KEY_ANSWER + " TEXT )";
        sqlitedatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        database = sqlitedatabase;
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(sqlitedatabase);
    }

    void addAnswer(String question, String answer) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Calendar calendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        values.put(KEY_TIMESTAMP, timestamp.toString());
        values.put(KEY_QUESTION, question);
        values.put(KEY_ANSWER, answer);

        database.insertOrThrow(TABLE_QUESTION, null, values);
    }

}
