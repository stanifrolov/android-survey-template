package com.example.stanislavfrolov.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class QuestionDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuizAppQuestions";
    private static final String TABLE_QUESTION = "quiz_questions";

    private static final String KEY_ID = "ID";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_OPTION_A = "OptionA";
    private static final String KEY_OPTION_B = "OptionB";
    private static final String KEY_OPTION_C = "OptionC";

    private SQLiteDatabase database;

    QuestionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        database = sqlitedatabase;

        String createTable = "CREATE TABLE IF NOT EXISTS "
                    + TABLE_QUESTION + " ( "
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_QUESTION + " TEXT, "
                    + KEY_OPTION_A + " TEXT, "
                    + KEY_OPTION_B + " TEXT, "
                    + KEY_OPTION_C + " TEXT )";
        sqlitedatabase.execSQL(createTable);

        addQuestionsToDatabase(sqlitedatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        database = sqlitedatabase;
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(sqlitedatabase);
    }

    private void addQuestionsToDatabase(SQLiteDatabase sqlitedatabase) {
        List<Question> allQuestions = new ArrayList<>();

        allQuestions.add(new Question("1 How do you feel today?", "Good", "Okay", "Bad"));
        allQuestions.add(new Question("2 Did you sleep well?", "Yes", "No", "Leave me alone"));

        for (Question question : allQuestions) {
            this.addQuestion(question, sqlitedatabase);
        }
    }

    private void addQuestion(Question question, SQLiteDatabase sqlitedatabase) {
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_OPTION_A, question.getOptionA());
        values.put(KEY_OPTION_B, question.getOptionB());
        values.put(KEY_OPTION_C, question.getOptionC());
        sqlitedatabase.insert(TABLE_QUESTION, null, values);
    }

    List<Question> getAllQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setOptionA(cursor.getString(2));
                question.setOptionB(cursor.getString(3));
                question.setOptionC(cursor.getString(4));
                allQuestions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allQuestions;
    }

}
