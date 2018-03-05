package com.example.marcin.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcin.quiz.service.manager.NetworkServiceManager;
import com.example.marcin.quiz.view.data.QuizViewData;

import java.util.List;

/**
 * Created by Marcin on 04.03.2018.
 */

public class QuizSummaryActivity extends AppCompatActivity {

    private int quizNumber;

    public static final String QUIZ_NUMBER = "com.example.marcin.quiz.QuizSummaryActivity.QUIZ_NUMBER";

    private static final int QUIZ_NUMBER_ARG_DEFAULT_VALUE = -1;
    private static String QUIZ_RESULT_TEXT = "Odpowiedziałeś poprawnie na %d%% pytań";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_summary);
        List<QuizViewData> quizViewDataList = NetworkServiceManager.getQuizViewDataList();

        TextView textViewResult = findViewById(R.id.txtv_result);

        Bundle boundle = getIntent().getExtras();
        if (boundle != null) {
            quizNumber = boundle.getInt(QuestionPagerActivity.QUIZ_NUMBER, QUIZ_NUMBER_ARG_DEFAULT_VALUE);
        }

        if(quizNumber != QUIZ_NUMBER_ARG_DEFAULT_VALUE){
            int percent = (int) Math.floor((quizViewDataList.get(quizNumber).getCountOfCorrectAnswers() * 100) / quizViewDataList.get(quizNumber).getCountOfQuestions());
            textViewResult.setText(String.format(QUIZ_RESULT_TEXT,percent));
        } else {
            textViewResult.setText("");
        }

        Button btnGoToQuizBeginning = findViewById(R.id.btn_go_to_quiz_beginning);
        btnGoToQuizBeginning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizSummaryActivity.this, QuestionPagerActivity.class);
                intent.putExtra(QUIZ_NUMBER, quizNumber);
                startActivity(intent);
                finish();
            }
        });

        Button btnGoToQuizList = findViewById(R.id.btn_go_to_quiz_list);
        btnGoToQuizList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
