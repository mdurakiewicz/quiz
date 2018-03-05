package com.example.marcin.quiz.db.model;

/**
 * Created by Marcin on 02.03.2018.
 */

public class Question {

    Long id;
    Long quizId;
    String text;

    public Question() {

    }
    public Question(Long quizId, String text) {
        this.quizId = quizId;
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", text='" + text + '\'' +
                '}';
    }
}
