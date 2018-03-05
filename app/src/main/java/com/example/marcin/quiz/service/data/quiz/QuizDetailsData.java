package com.example.marcin.quiz.service.data.quiz;

import java.util.List;

/**
 * Created by Marcin on 01.03.2018.
 */

public class QuizDetailsData {

    String title;
    String type;
    Long id;
    List<QuestionData> questions;

    public QuizDetailsData(String title, String type, Long id, List<QuestionData> questions) {
        this.title = title;
        this.type = type;
        this.id = id;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionData> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuizDetailsData{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", questions=" + questions +
                '}';
    }
}
