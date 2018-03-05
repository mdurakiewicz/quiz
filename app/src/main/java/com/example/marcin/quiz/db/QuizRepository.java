package com.example.marcin.quiz.db;

import java.util.List;

/**
 * Created by Marcin on 28.02.2018.
 */

public interface QuizRepository<T> {

    void add(T item);

    void add(Iterable<T> items);

    List<T> getAll();

    void deleteAll();

    void update(Long id, int countOfGivenAnswers, int countOfCorrectAnswers);
}
