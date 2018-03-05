package com.example.marcin.quiz.db.table;

import android.provider.BaseColumns;

/**
 * Created by Marcin on 03.03.2018.
 */

public class AnswerReaderContract {

    private AnswerReaderContract(){}

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AnswerEntry.TABLE_NAME + " (" +
                    AnswerEntry._ID + " INTEGER PRIMARY KEY," +
                    AnswerEntry.QUESTION_ID_COLUMN_NAME + " INTEGER," +
                    AnswerEntry.TEXT_COLUMN_NAME+ " TEXT, " +
                    AnswerEntry.IS_CORRECT_COLUMN_NAME+ " INTEGER, " +
                    "FOREIGN KEY(" + AnswerEntry.QUESTION_ID_COLUMN_NAME + ") REFERENCES " + QuestionReaderContract.QuestionEntry.TABLE_NAME + "(" + QuestionReaderContract.QuestionEntry._ID + "));";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AnswerEntry.TABLE_NAME;

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static class AnswerEntry implements BaseColumns {

        public static final String TABLE_NAME = "answer";
        public static final String QUESTION_ID_COLUMN_NAME = "question_id";
        public static final String TEXT_COLUMN_NAME = "text";
        public static final String IS_CORRECT_COLUMN_NAME = "is_correct";
    }
}
