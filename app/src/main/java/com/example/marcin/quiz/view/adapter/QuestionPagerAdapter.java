package com.example.marcin.quiz.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.marcin.quiz.QuestionPagerActivity;
import com.example.marcin.quiz.service.manager.NetworkServiceManager;
import com.example.marcin.quiz.view.data.QuizViewData;
import com.example.marcin.quiz.view.fragment.QuestionPageFragment;

import java.util.List;

/**
 * Created by Marcin on 03.03.2018.
 */

public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    private int questionsCount = 0;
    private int quizNumber = 0;

    public QuestionPagerAdapter(FragmentManager fm, int questionsCount, int quizNumber) {
        super(fm);
        this.questionsCount = questionsCount;
        this.quizNumber = quizNumber;
    }

    public int getQuestionCount() {
        return questionsCount;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionPageFragment questionPageFragment = new QuestionPageFragment();

        List<QuizViewData> quizViewDataList = NetworkServiceManager.getQuizViewDataList();

        int answersCount = quizViewDataList.get(quizNumber).getQuestions().get(position).getAnswers().size();

        Bundle bundle = new Bundle();
        bundle.putInt(QuestionPagerActivity.QUIZ_NUMBER, quizNumber);
        bundle.putInt(QuestionPagerActivity.QUESTION_NUMBER, position);
        bundle.putInt(QuestionPagerActivity.ANSWERS_COUNT, answersCount);
        questionPageFragment.setArguments(bundle);

        return questionPageFragment;
    }

    @Override
    public int getCount() {
        return questionsCount;
    }


}
