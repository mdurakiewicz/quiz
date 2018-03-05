package com.example.marcin.quiz.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.marcin.quiz.db.table.AnswerReaderContract;
import com.example.marcin.quiz.db.table.QuestionReaderContract;
import com.example.marcin.quiz.db.table.QuizReaderContract;

/**
 * Created by Marcin on 28.02.2018.
 */

public class QuizDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quiz.db";

    public QuizDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QuizReaderContract.getSqlCreateEntries());
        sqLiteDatabase.execSQL(QuestionReaderContract.getSqlCreateEntries());
        sqLiteDatabase.execSQL(AnswerReaderContract.getSqlCreateEntries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(AnswerReaderContract.getSqlDeleteEntries());
        sqLiteDatabase.execSQL(QuestionReaderContract.getSqlDeleteEntries());
        sqLiteDatabase.execSQL(QuizReaderContract.getSqlDeleteEntries());
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
