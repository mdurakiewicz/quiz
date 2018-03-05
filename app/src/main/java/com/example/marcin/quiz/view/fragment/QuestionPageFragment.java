package com.example.marcin.quiz.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcin.quiz.MainActivity;
import com.example.marcin.quiz.QuestionPagerActivity;
import com.example.marcin.quiz.QuizSummaryActivity;
import com.example.marcin.quiz.R;
import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.repositoryImpl.QuizRepositoryImpl;
import com.example.marcin.quiz.service.manager.NetworkServiceManager;
import com.example.marcin.quiz.view.adapter.QuestionPagerAdapter;
import com.example.marcin.quiz.view.data.QuizViewData;

import java.util.List;

/**
 * Created by Marcin on 03.03.2018.
 */

public class QuestionPageFragment extends Fragment {

    private int quizNumber;
    private int questionNumber;
    private int answersCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.questiton, container, false);

        quizNumber = 0;
        questionNumber = 0;
        answersCount = 0;

        List<QuizViewData> quizViewDataList = NetworkServiceManager.getQuizViewDataList();

        Bundle bundle = getArguments();
        if(bundle != null){
            quizNumber = bundle.getInt(QuestionPagerActivity.QUIZ_NUMBER);
            questionNumber = bundle.getInt(QuestionPagerActivity.QUESTION_NUMBER);
            answersCount = bundle.getInt(QuestionPagerActivity.ANSWERS_COUNT);
        }

        TextView txtVTitle = rootView.findViewById(R.id.txtV_title);
        ProgressBar prBar = rootView.findViewById(R.id.prB_progress);
        RadioGroup radioGroup = rootView.findViewById(R.id.rGrp_answers);

        txtVTitle.setText(quizViewDataList.get(quizNumber).getQuestions().get(questionNumber).getText());

        int progress = (int)Math.floor((questionNumber * 100)/(quizViewDataList.get(quizNumber).getCountOfQuestions()));
        prBar.setProgress(progress);


        for(int i = 0; i < quizViewDataList.get(quizNumber).getQuestions().get(questionNumber).getAnswers().size(); i++) {
            String answer = quizViewDataList.get(quizNumber).getQuestions().get(questionNumber).getAnswers().get(i).getText();
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(answer);
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int position = 0;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    if (radioGroup.getChildAt(i).getId() == checkedId) {
                        position = i;
                        break;
                    }
                }

                List<QuizViewData> quizViewDataList = NetworkServiceManager.getQuizViewDataList();

                if(quizViewDataList.get(quizNumber).getQuestions().get(questionNumber).getAnswers().get(position).getCorrect() == 1){
                    quizViewDataList.get(quizNumber).setCountOfCorrectAnswers(quizViewDataList.get(quizNumber).getCountOfCorrectAnswers() + 1);
                }

                quizViewDataList.get(quizNumber).setCountOfGivenAnswers(quizViewDataList.get(quizNumber).getCountOfGivenAnswers() + 1);

                QuizDBHelper quizDBHelper = new QuizDBHelper(getContext());
                QuizRepositoryImpl quizRepository = new QuizRepositoryImpl(quizDBHelper);
                quizRepository.update(quizViewDataList.get(quizNumber).getId(), quizViewDataList.get(quizNumber).getCountOfGivenAnswers(),
                        quizViewDataList.get(quizNumber).getCountOfCorrectAnswers());

                if(questionNumber < quizViewDataList.get(quizNumber).getQuestions().size() - 1){
                    ((QuestionPagerActivity)getActivity()).setCurrentItem (questionNumber + 1, true);
                } else {
                    Intent intent = new Intent(getContext(), QuizSummaryActivity.class);
                    intent.putExtra(QuestionPagerActivity.QUIZ_NUMBER, quizNumber);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return rootView;
    }
}
