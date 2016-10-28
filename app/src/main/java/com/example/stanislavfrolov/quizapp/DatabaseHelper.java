package com.example.stanislavfrolov.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuizApp";
    private static final String TABLE_QUESTION = "quiz_table";
    private static final String KEY_ID = "ID";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_OPTION_A = "OptionA";
    private static final String KEY_OPTION_B = "OptionB";
    private static final String KEY_OPTION_C = "OptionC";
    private static final String KEY_ANSWER = "Answer";

    private SQLiteDatabase database;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
                    + KEY_OPTION_C + " TEXT, "
                    + KEY_ANSWER + " TEXT )";
        sqlitedatabase.execSQL(createTable);
        addQuestions(sqlitedatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        database = sqlitedatabase;
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(sqlitedatabase);
    }

    private void addQuestions(SQLiteDatabase sqlitedatabase) {
        Question q1 = new Question("How do you feel today?", "Good", "Okay", "Bad", "");
        this.addQuestion(q1, sqlitedatabase);

        Question q2 = new Question("Did you work out today?", "Yes", "No", "Later", "");
        this.addQuestion(q2, sqlitedatabase);
    }

    private void addQuestion(Question question, SQLiteDatabase sqlitedatabase) {
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_OPTION_A, question.getOptionA());
        values.put(KEY_OPTION_B, question.getOptionB());
        values.put(KEY_OPTION_C, question.getOptionC());
        values.put(KEY_ANSWER, question.getAnswer());
        sqlitedatabase.insert(TABLE_QUESTION, null, values);
    }

    public void addAnswerToQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, question.getId());
        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_OPTION_A, question.getOptionA());
        values.put(KEY_OPTION_B, question.getOptionB());
        values.put(KEY_OPTION_C, question.getOptionC());
        values.put(KEY_ANSWER, question.getAnswer());
        database.replace(TABLE_QUESTION, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> allQuestions = new ArrayList<Question>();
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
                question.setAnswer(cursor.getString(5));
                allQuestions.add(question);
            } while (cursor.moveToNext());
        }
        return allQuestions;
    }

    public ArrayList<Cursor> getData(String Query) {
        database = this.getReadableDatabase();
        String[] columns = new String[]{"message"};
        ArrayList<Cursor> allCursor = new ArrayList<Cursor>(2);
        MatrixCursor matrixCursor = new MatrixCursor(columns);
        allCursor.add(null);
        allCursor.add(null);

        try {
            String maxQuery = Query;
            Cursor c = database.rawQuery(maxQuery, null);
            matrixCursor.addRow(new Object[]{"success"});
            allCursor.set(1, matrixCursor);
            if (null != c && c.getCount() > 0) {
                allCursor.set(0, c);
                c.moveToFirst();
                return allCursor;
            }
            return allCursor;
        } catch (SQLiteException sqliteEx) {
            Log.d("printing exception", sqliteEx.getMessage());
            matrixCursor.addRow(new Object[]{"" + sqliteEx.getMessage()});
            allCursor.set(1, matrixCursor);
            return allCursor;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());
            matrixCursor.addRow(new Object[]{"" + ex.getMessage()});
            allCursor.set(1, matrixCursor);
            return allCursor;
        }

    }

}
