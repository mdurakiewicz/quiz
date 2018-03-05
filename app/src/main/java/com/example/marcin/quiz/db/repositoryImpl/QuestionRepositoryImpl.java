package com.example.marcin.quiz.db.repositoryImpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcin.quiz.db.QuestionRepository;
import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.model.Question;
import com.example.marcin.quiz.db.model.Quiz;
import com.example.marcin.quiz.db.table.QuestionReaderContract;
import com.example.marcin.quiz.db.table.QuizReaderContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marcin on 03.03.2018.
 */

public class QuestionRepositoryImpl implements QuestionRepository<Question> {

    private final QuizDBHelper quizDBHelper;

    public QuestionRepositoryImpl(QuizDBHelper quizDBHelper){
        this.quizDBHelper = quizDBHelper;
    }

    @Override
    public Long add(Question item) {
        final SQLiteDatabase db = quizDBHelper.getWritableDatabase();
        db.beginTransaction();
        Long id;

        try {
            ContentValues values = new ContentValues();
            values.put(QuestionReaderContract.QuestionEntry.QUIZ_ID_COLUMN_NAME, item.getQuizId());
            values.put(QuestionReaderContract.QuestionEntry.TEXT_COLUMN_NAME, item.getText());

            id = db.insert(QuestionReaderContract.QuestionEntry.TABLE_NAME, null, values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return id;
    }

    @Override
    public void add(Iterable<Question> items) {
        final SQLiteDatabase db = quizDBHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            for (Question item : items) {
                ContentValues values = new ContentValues();
                values.put(QuestionReaderContract.QuestionEntry.QUIZ_ID_COLUMN_NAME, item.getQuizId());
                values.put(QuestionReaderContract.QuestionEntry.TEXT_COLUMN_NAME, item.getText());

                db.insert(QuestionReaderContract.QuestionEntry.TABLE_NAME, null, values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public List<Question> getAll() {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();
        ArrayList<Question> questionList = new ArrayList<Question>();

        try {

            String[] projection = {
                    QuestionReaderContract.QuestionEntry._ID,
                    QuestionReaderContract.QuestionEntry.QUIZ_ID_COLUMN_NAME,
                    QuestionReaderContract.QuestionEntry.TEXT_COLUMN_NAME
            };

            Cursor cursor = db.query(QuestionReaderContract.QuestionEntry.TABLE_NAME,projection,null,null,null,null,null);

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);

                Question question = new Question();
                question.setId(cursor.getLong(cursor.getColumnIndex(QuestionReaderContract.QuestionEntry._ID)));
                question.setQuizId(cursor.getLong(cursor.getColumnIndex(QuestionReaderContract.QuestionEntry.QUIZ_ID_COLUMN_NAME)));
                question.setText(cursor.getString(cursor.getColumnIndex(QuestionReaderContract.QuestionEntry.TEXT_COLUMN_NAME)));

                questionList.add(question);
            }

            cursor.close();

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return questionList;
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = quizDBHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            db.delete(QuestionReaderContract.QuestionEntry.TABLE_NAME,null,null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
