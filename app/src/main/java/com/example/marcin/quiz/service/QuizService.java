package com.example.marcin.quiz.service;

import com.example.marcin.quiz.service.data.quiz.QuizDetailsData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Marcin on 01.03.2018.
 */

public interface QuizService {

    @GET("api/v1/quiz/{id_quizu}/0")
    Observable<QuizDetailsData> getQuizDetails(@Path("id_quizu") Long quizId);
}
