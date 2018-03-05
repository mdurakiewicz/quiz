package com.example.marcin.quiz.db.repositoryImpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.marcin.quiz.db.QuizRepository;
import com.example.marcin.quiz.db.model.Quiz;
import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.table.QuizReaderContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marcin on 28.02.2018.
 */

public class QuizRepositoryImpl implements QuizRepository<Quiz> {

    private final QuizDBHelper quizDBHelper;

    public QuizRepositoryImpl(QuizDBHelper quizDBHelper){
        this.quizDBHelper = quizDBHelper;
    }

    @Override
    public void add(Quiz item) {

        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Quiz> items) {
        final SQLiteDatabase db = quizDBHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            for (Quiz item : items) {
                ContentValues values = new ContentValues();
                values.put(QuizReaderContract.QuizEntry._ID, item.getId());
                values.put(QuizReaderContract.QuizEntry.COUNT_OF_CORRECT_ANSWERS_COLUMN_NAME, item.getCountOfCorrectAnswers());
                values.put(QuizReaderContract.QuizEntry.COUNT_OF_GIVEN_ANSWERS_COLUMN_NAME, item.getCountOfGivenAnswers());
                values.put(QuizReaderContract.QuizEntry.COUNT_OF_QUESTIONS_COLUMN_NAME, item.getCountOfQuestions());
                values.put(QuizReaderContract.QuizEntry.TITLE_COLUMN_NAME, item.getTitle());
                values.put(QuizReaderContract.QuizEntry.TYPE_COLUMN_NAME, item.getType());

                db.insert(QuizReaderContract.QuizEntry.TABLE_NAME, null, values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public List<Quiz> getAll() {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {

            String[] projection = {
                    QuizReaderContract.QuizEntry._ID,
                    QuizReaderContract.QuizEntry.COUNT_OF_CORRECT_ANSWERS_COLUMN_NAME,
                    QuizReaderContract.QuizEntry.COUNT_OF_GIVEN_ANSWERS_COLUMN_NAME,
                    QuizReaderContract.QuizEntry.COUNT_OF_QUESTIONS_COLUMN_NAME,
                    QuizReaderContract.QuizEntry.TITLE_COLUMN_NAME,
                    QuizReaderContract.QuizEntry.TYPE_COLUMN_NAME
            };

            Cursor cursor = db.query(QuizReaderContract.QuizEntry.TABLE_NAME,projection,null,null,null,null,null);

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);

                Quiz quiz = new Quiz();
                quiz.setId(cursor.getLong(cursor.getColumnIndex(QuizReaderContract.QuizEntry._ID)));
                quiz.setCountOfCorrectAnswers(cursor.getInt(cursor.getColumnIndex(QuizReaderContract.QuizEntry.COUNT_OF_CORRECT_ANSWERS_COLUMN_NAME)));
                quiz.setCountOfGivenAnswers(cursor.getInt(cursor.getColumnIndex(QuizReaderContract.QuizEntry.COUNT_OF_GIVEN_ANSWERS_COLUMN_NAME)));
                quiz.setCountOfQuestions(cursor.getInt(cursor.getColumnIndex(QuizReaderContract.QuizEntry.COUNT_OF_QUESTIONS_COLUMN_NAME)));
                quiz.setTitle(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.TITLE_COLUMN_NAME)));
                quiz.setType(cursor.getString(cursor.getColumnIndex(QuizReaderContract.QuizEntry.TYPE_COLUMN_NAME)));

                quizList.add(quiz);
            }

            cursor.close();

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return quizList;
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            db.delete(QuizReaderContract.QuizEntry.TABLE_NAME,null,null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

    }

    @Override
    public void update(Long id, int countOfGivenAnswers, int countOfCorrectAnswers) {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(QuizReaderContract.QuizEntry.COUNT_OF_GIVEN_ANSWERS_COLUMN_NAME, countOfGivenAnswers);
            values.put(QuizReaderContract.QuizEntry.COUNT_OF_CORRECT_ANSWERS_COLUMN_NAME, countOfCorrectAnswers);

            String selection = QuizReaderContract.QuizEntry._ID + " = ?";
            String[] selectionArgs = { String.valueOf(id) };

            int count = db.update(
                QuizReaderContract.QuizEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
