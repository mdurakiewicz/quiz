package com.example.marcin.quiz.service.client;

import com.example.marcin.quiz.service.QuizzesService;
import com.example.marcin.quiz.service.data.quizzes.QuizzesData;
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

public class QuizzesClient {

    private static final String QUIZZES_BASE_URL = "http://quiz.o2.pl/";
    private static QuizzesClient  instance;
    private QuizzesService quizzesService;

    private QuizzesClient() {
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(QUIZZES_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        quizzesService = retrofit.create(QuizzesService.class);
    }

    public static QuizzesClient getInstance() {
        if (instance == null) {
            instance = new QuizzesClient();
        }
        return instance;
    }

    public Observable<QuizzesData> getQuizzes() {
        return quizzesService.getQuizzes();
    }
}
