package com.example.marcin.quiz;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.repositoryImpl.QuizRepositoryImpl;
import com.example.marcin.quiz.service.manager.NetworkServiceManager;
import com.example.marcin.quiz.view.adapter.QuestionPagerAdapter;
import com.example.marcin.quiz.view.data.QuizViewData;

import java.util.List;

/**
 * Created by Marcin on 03.03.2018.
 */

public class QuestionPagerActivity extends FragmentActivity {

    public static int countOfQuestions;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int quizNumber;

    public static final String QUIZ_NUMBER = "com.example.marcin.quiz.QuestionPagerActivity.QUIZ_NUMBER";
    public static final String QUESTION_NUMBER = "com.example.marcin.quiz.QuestionPagerActivity.QUESTION_NUMBER";
    public static final String ANSWERS_COUNT = "com.example.marcin.quiz.QuestionPagerActivity.ANSWERS_COUNT";

    private static final int QUIZ_NUMBER_ARG_DEFAULT_VALUE = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_pager);
        List<QuizViewData> quizViewDataList = NetworkServiceManager.getQuizViewDataList();
        countOfQuestions = 1;

        Bundle boundle = getIntent().getExtras();
        if (boundle != null) {
            quizNumber = boundle.getInt(MainActivity.QUIZ_NUMBER, QUIZ_NUMBER_ARG_DEFAULT_VALUE);

            if(quizNumber == QUIZ_NUMBER_ARG_DEFAULT_VALUE){
                quizNumber = boundle.getInt(QuizSummaryActivity.QUIZ_NUMBER, QUIZ_NUMBER_ARG_DEFAULT_VALUE);
            }

            if(quizNumber == QUIZ_NUMBER_ARG_DEFAULT_VALUE){
                quizNumber = 0;
            }

            if(quizNumber > 0 && quizNumber < quizViewDataList.size()){
                countOfQuestions = quizViewDataList.get(quizNumber).getCountOfQuestions();
            }
        }

        View touchView = findViewById(R.id.pager);
        touchView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        mPager = findViewById(R.id.pager);

        mPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), countOfQuestions, quizNumber);
        mPager.setAdapter(mPagerAdapter);

        quizViewDataList = NetworkServiceManager.getQuizViewDataList();

        int quizPosition = 0;
        if(quizViewDataList.get(quizNumber).getCountOfQuestions() == quizViewDataList.get(quizNumber).getCountOfGivenAnswers()){
            quizViewDataList.get(quizNumber).setCountOfGivenAnswers(0);
            quizViewDataList.get(quizNumber).setCountOfCorrectAnswers(0);
        } else {
            quizPosition = quizViewDataList.get(quizNumber).getCountOfGivenAnswers();
        }

        QuizDBHelper quizDBHelper = new QuizDBHelper(getApplicationContext());
        QuizRepositoryImpl quizRepository = new QuizRepositoryImpl(quizDBHelper);
        quizRepository.update(quizViewDataList.get(quizNumber).getId(), quizViewDataList.get(quizNumber).getCountOfGivenAnswers(),
                quizViewDataList.get(quizNumber).getCountOfCorrectAnswers());

        mPager.setCurrentItem(quizPosition, true);
    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        mPager.setCurrentItem(item, smoothScroll);
    }

}
