package com.example.marcin.quiz.service;

import com.example.marcin.quiz.service.data.quizzes.QuizzesData;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Marcin on 01.03.2018.
 */

public interface QuizzesService {

    @GET("api/v1/quizzes/0/100")
    Observable<QuizzesData> getQuizzes();

}

