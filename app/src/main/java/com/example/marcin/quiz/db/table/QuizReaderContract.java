package com.example.marcin.quiz.db.table;

import android.provider.BaseColumns;

/**
 * Created by Marcin on 28.02.2018.
 */

public class QuizReaderContract {

    private QuizReaderContract() {}

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuizEntry.TABLE_NAME + " (" +
                    QuizEntry._ID + " INTEGER PRIMARY KEY," +
                    QuizEntry.COUNT_OF_QUESTIONS_COLUMN_NAME + " INTEGER," +
                    QuizEntry.COUNT_OF_CORRECT_ANSWERS_COLUMN_NAME + " INTEGER," +
                    QuizEntry.COUNT_OF_GIVEN_ANSWERS_COLUMN_NAME + " INTEGER," +
                    QuizEntry.TYPE_COLUMN_NAME + " TEXT," +
                    QuizEntry.TITLE_COLUMN_NAME + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuizEntry.TABLE_NAME;

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static class QuizEntry implements BaseColumns {

        public static final String TABLE_NAME = "quiz";
        public static final String COUNT_OF_QUESTIONS_COLUMN_NAME = "count_of_questions";
        public static final String COUNT_OF_CORRECT_ANSWERS_COLUMN_NAME = "count_of_correct_answers";
        public static final String COUNT_OF_GIVEN_ANSWERS_COLUMN_NAME = "count_of_given_answers";
        public static final String TITLE_COLUMN_NAME = "title";
        public static final String TYPE_COLUMN_NAME = "type";
    }
}
