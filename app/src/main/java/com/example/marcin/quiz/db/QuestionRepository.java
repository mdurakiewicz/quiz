package com.example.marcin.quiz.db;

import java.util.List;

/**
 * Created by Marcin on 03.03.2018.
 */

public interface QuestionRepository<T> {

    Long add(T item);

    void add(Iterable<T> items);

    List<T> getAll();

    void deleteAll();
}
