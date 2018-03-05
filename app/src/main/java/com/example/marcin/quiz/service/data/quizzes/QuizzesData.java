package com.example.marcin.quiz.service.data.quizzes;

import java.util.List;

/**
 * Created by Marcin on 01.03.2018.
 */

public class QuizzesData {

    Integer count;
    List<QuizData> items;

    public QuizzesData(Integer count, List<QuizData> items){
        this.count = count;
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<QuizData> getItems() {
        return items;
    }

    public void setItems(List<QuizData> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "QuizzesData{" +
                "count=" + count +
                ", items=" + items +
                '}';
    }
}
