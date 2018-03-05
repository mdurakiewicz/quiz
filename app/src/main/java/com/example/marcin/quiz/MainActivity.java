package com.example.marcin.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.model.Quiz;
import com.example.marcin.quiz.db.repositoryImpl.QuizRepositoryImpl;
import com.example.marcin.quiz.service.manager.NetworkServiceManager;
import com.example.marcin.quiz.view.adapter.QuizListAdapter;
import com.example.marcin.quiz.view.data.QuizViewData;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NetworkServiceManager networkServiceManager;
    private QuizListAdapter quizListAdapter;
    private QuizDBHelper quizDBHelper;

    public static final String QUIZ_NUMBER = "com.example.marcin.quiz.MainActivity.QUIZ_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView quizListView = findViewById(R.id.quizzes);


        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, QuestionPagerActivity.class);
                intent.putExtra(QUIZ_NUMBER, position);
                startActivity(intent);
            }
        });

        quizDBHelper = new QuizDBHelper(getApplicationContext());

        quizListAdapter = new QuizListAdapter(getApplicationContext());
        quizListView.setAdapter(quizListAdapter);

        networkServiceManager = NetworkServiceManager.getInstance();

        List<QuizViewData> quizViewDataList = networkServiceManager.getQuizViewDataFromDb(quizDBHelper);
        quizListAdapter.setQuizList(quizViewDataList);

        networkServiceManager.getQuizzes(quizDBHelper, quizListAdapter);

    }

    @Override
    protected void onDestroy() {
        quizDBHelper.close();

        if (networkServiceManager.getQuizzesSubscription()!= null && !networkServiceManager.getQuizzesSubscription().isUnsubscribed()) {
            networkServiceManager.getQuizzesSubscription().unsubscribe();
        }

        List<Subscription> quizDetailsSubscriptionList = networkServiceManager.getQuizDetailsSubscritpionList();

        for(Subscription subscription : quizDetailsSubscriptionList){
            if (subscription!= null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }

        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();

        List<QuizViewData> quizViewDataList = NetworkServiceManager.getQuizViewDataList();
        quizListAdapter.setQuizList(quizViewDataList);
    }


}
