package com.example.stanislavfrolov.quizapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class AnswerDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuizAppUser";
    private static final String TABLE_QUESTION = "quiz_user";

    private static final String KEY_TIMESTAMP = "Timestamp";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_ANSWER = "Answer";

    private SQLiteDatabase database;

    AnswerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        database = sqlitedatabase;

        createTableIfNotExists(sqlitedatabase);
    }

    private void createTableIfNotExists(SQLiteDatabase sqlitedatabase) {
        String createTable = getCreateTableQuery();

        sqlitedatabase.execSQL(createTable);
    }

    @NonNull
    private String getCreateTableQuery() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_QUESTION + " ( "
                + KEY_TIMESTAMP + " TEXT, "
                + KEY_QUESTION + " TEXT, "
                + KEY_ANSWER + " TEXT, "
                + "CONSTRAINT unique_key UNIQUE ( "
                + KEY_TIMESTAMP + " ) )";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        database = sqlitedatabase;

        dropTableIfExists(sqlitedatabase);
    }

    private void dropTableIfExists(SQLiteDatabase sqlitedatabase) {
        sqlitedatabase.execSQL(getDropTableQuery());

        onCreate(sqlitedatabase);
    }

    @NonNull
    private String getDropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_QUESTION;
    }

    List<Answer> getAllAnsweredQuestions() {
        List<Answer> allAnswers = new ArrayList<>();

        Cursor cursor = getAllFromTable();

        if (cursor.moveToFirst()) {
            do {
                Answer answer = getAnswerAtCurrentCursor(cursor);
                allAnswers.add(answer);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return allAnswers;
    }

    private Cursor getAllFromTable() {
        database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;

        return database.rawQuery(selectQuery, null);
    }

    @NonNull
    private Answer getAnswerAtCurrentCursor(Cursor cursor) {
        Answer answer = new Answer();

        answer.setTimestamp(cursor.getString(0));
        answer.setQuestion(cursor.getString(1));
        answer.setAnswer(cursor.getString(2));

        return answer;
    }

    void addAnswer(String question, String answer, String timestamp) {
        database = this.getWritableDatabase();

        insertOrReplaceAnswer(question, answer, timestamp);
    }

    private void insertOrReplaceAnswer(String question, String answer, String timestamp) {
        String replaceAnswer = getInsertOrReplaceQuery(question, answer, timestamp);

        database.execSQL(replaceAnswer);
    }

    @NonNull
    private String getInsertOrReplaceQuery(String question, String answer, String timestamp) {
        return "INSERT OR REPLACE INTO "
                + TABLE_QUESTION +
                " VALUES (\""
                + timestamp + "\", \""
                + question + "\", \""
                + answer + "\")";
    }
}
