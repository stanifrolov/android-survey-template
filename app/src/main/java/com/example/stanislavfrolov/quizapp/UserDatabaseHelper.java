package com.example.stanislavfrolov.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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
                + KEY_ANSWER + " TEXT, "
                + "CONSTRAINT unique_key UNIQUE ( "
                + KEY_TIMESTAMP + " ) )";
        sqlitedatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        database = sqlitedatabase;
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(sqlitedatabase);
    }

    void addAnswer(String question, String answer, String timestamp) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TIMESTAMP, timestamp);
        values.put(KEY_QUESTION, question);
        values.put(KEY_ANSWER, answer);

        String replaceAnswer = "INSERT OR REPLACE INTO "
                + TABLE_QUESTION + " VALUES (\""
                + timestamp + "\", \""
                + question + "\", \""
                + answer + "\")";

        database.execSQL(replaceAnswer);
    }

    List<Answer> getAllAnsweredQuestions() {
        List<Answer> allAnswers = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setTimestamp(cursor.getString(0));
                answer.setQuestion(cursor.getString(1));
                answer.setAnswer(cursor.getString(2));
                allAnswers.add(answer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allAnswers;
    }
}
