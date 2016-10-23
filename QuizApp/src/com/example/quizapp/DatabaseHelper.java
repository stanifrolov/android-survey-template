package com.example.quizapp;

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
    private static final String KEY_CORRECT_ANSWER = "CorrectAnswer";
    private static final String KEY_OPTION_A = "OptionA";
    private static final String KEY_OPTION_B = "OptionB";
    private static final String KEY_OPTION_C = "OptionC";
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        database = sqlitedatabase;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUESTION
                + " TEXT, " + KEY_CORRECT_ANSWER + " TEXT, " + KEY_OPTION_A + " TEXT, "
                + KEY_OPTION_B + " TEXT, " + KEY_OPTION_C + " TEXT)";
        sqlitedatabase.execSQL(sql);
        addQuestions();
        // TODO: 21.10.2016 Check if this is needed
        // sqlitedatabase.close();
    }

    private void addQuestions() {
        Question question = new Question("How do you feel?", "Good", "Okay", "Bad", "Any answer is correct");
        this.addQuestion(question);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldV, int newV) {
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(sqlitedatabase);
    }

    public void addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_CORRECT_ANSWER, question.getCorrectAnswer());
        values.put(KEY_OPTION_A, question.getOptionA());
        values.put(KEY_OPTION_B, question.getOptionB());
        values.put(KEY_OPTION_C, question.getOptionC());
        database.insert(TABLE_QUESTION, null, values);
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
                question.setCorrectAnswer(cursor.getString(2));
                question.setOptionA(cursor.getString(3));
                question.setOptionB(cursor.getString(4));
                question.setOptionC(cursor.getString(5));
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
