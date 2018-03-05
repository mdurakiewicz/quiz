package com.example.marcin.quiz.db.repositoryImpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcin.quiz.db.AnswerRepository;
import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.model.Answer;
import com.example.marcin.quiz.db.table.AnswerReaderContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marcin on 03.03.2018.
 */

public class AnswerRepositoryImpl implements AnswerRepository<Answer> {

    private final QuizDBHelper quizDBHelper;

    public AnswerRepositoryImpl(QuizDBHelper quizDBHelper){
        this.quizDBHelper = quizDBHelper;
    }


    @Override
    public Long add(Answer item){
        final SQLiteDatabase db = quizDBHelper.getWritableDatabase();
        db.beginTransaction();
        Long id;

        try {
            ContentValues values = new ContentValues();
            values.put(AnswerReaderContract.AnswerEntry.QUESTION_ID_COLUMN_NAME, item.getQuestionId());
            values.put(AnswerReaderContract.AnswerEntry.TEXT_COLUMN_NAME, item.getText());
            values.put(AnswerReaderContract.AnswerEntry.IS_CORRECT_COLUMN_NAME, item.getCorrect());

            id = db.insert(AnswerReaderContract.AnswerEntry.TABLE_NAME, null, values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return id;
    }

    @Override
    public void add(Iterable<Answer> items) {
        final SQLiteDatabase db = quizDBHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            for (Answer item : items) {
                ContentValues values = new ContentValues();
                values.put(AnswerReaderContract.AnswerEntry.QUESTION_ID_COLUMN_NAME, item.getQuestionId());
                values.put(AnswerReaderContract.AnswerEntry.TEXT_COLUMN_NAME, item.getText());
                values.put(AnswerReaderContract.AnswerEntry.IS_CORRECT_COLUMN_NAME, item.getCorrect());

                db.insert(AnswerReaderContract.AnswerEntry.TABLE_NAME, null, values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public List<Answer> getAll() {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();
        ArrayList<Answer> answerList = new ArrayList<Answer>();

        try {

            String[] projection = {
                    AnswerReaderContract.AnswerEntry._ID,
                    AnswerReaderContract.AnswerEntry.QUESTION_ID_COLUMN_NAME,
                    AnswerReaderContract.AnswerEntry.TEXT_COLUMN_NAME,
                    AnswerReaderContract.AnswerEntry.IS_CORRECT_COLUMN_NAME
            };

            Cursor cursor = db.query(AnswerReaderContract.AnswerEntry.TABLE_NAME,projection,null,null,null,null,null);

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);

                Answer answer = new Answer();
                answer.setId(cursor.getLong(cursor.getColumnIndex(AnswerReaderContract.AnswerEntry._ID)));
                answer.setQuestionId(cursor.getLong(cursor.getColumnIndex(AnswerReaderContract.AnswerEntry.QUESTION_ID_COLUMN_NAME)));
                answer.setText(cursor.getString(cursor.getColumnIndex(AnswerReaderContract.AnswerEntry.TEXT_COLUMN_NAME)));
                answer.setCorrect(cursor.getInt(cursor.getColumnIndex(AnswerReaderContract.AnswerEntry.IS_CORRECT_COLUMN_NAME)));

                answerList.add(answer);
            }

            cursor.close();

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return answerList;
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            db.delete(AnswerReaderContract.AnswerEntry.TABLE_NAME,null,null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
