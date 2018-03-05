package com.example.marcin.quiz.service.client;

import android.support.annotation.NonNull;

import com.example.marcin.quiz.service.QuizService;
import com.example.marcin.quiz.service.data.quiz.QuizDetailsData;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Marcin on 01.03.2018.
 */

public class QuizClient {

    private static final String QUIZ_BASE_URL = "http://quiz.o2.pl/";
    private static QuizClient  instance;
    private QuizService quizService;

    private QuizClient() {
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(QUIZ_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        quizService = retrofit.create(QuizService.class);
    }

    public static QuizClient getInstance() {
        if (instance == null) {
            instance = new QuizClient();
        }
        return instance;
    }

    public Observable<QuizDetailsData> getQuizDetails(@NonNull Long quizId) {
        return quizService.getQuizDetails(quizId);
    }

}
