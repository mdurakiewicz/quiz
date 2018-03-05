package com.example.marcin.quiz.service.data.quizzes;

/**
 * Created by Marcin on 01.03.2018.
 */

public class QuizData {

    Integer questions;
    Long id;
    String title;
    String type;

    public QuizData(Integer questions, Long id, String title, String type){
        this.questions = questions;
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "QuizData{" +
                "questions=" + questions +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
