package com.example.marcin.quiz.db.table;

import android.provider.BaseColumns;

/**
 * Created by Marcin on 03.03.2018.
 */

public class QuestionReaderContract {

    private QuestionReaderContract(){}

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuestionEntry.TABLE_NAME + " (" +
                    QuestionEntry._ID + " INTEGER PRIMARY KEY," +
                    QuestionEntry.QUIZ_ID_COLUMN_NAME + " INTEGER," +
                    QuestionEntry.TEXT_COLUMN_NAME+ " TEXT," +
                    "FOREIGN KEY(" + QuestionEntry.QUIZ_ID_COLUMN_NAME + ") REFERENCES " + QuizReaderContract.QuizEntry.TABLE_NAME + "(" + QuizReaderContract.QuizEntry._ID + "));";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuestionEntry.TABLE_NAME;

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static class QuestionEntry implements BaseColumns {

        public static final String TABLE_NAME = "question";
        public static final String QUIZ_ID_COLUMN_NAME = "quiz_id";
        public static final String TEXT_COLUMN_NAME = "text";
    }
}
