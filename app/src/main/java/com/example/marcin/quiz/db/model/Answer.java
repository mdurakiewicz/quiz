package com.example.marcin.quiz.db.model;

/**
 * Created by Marcin on 02.03.2018.
 */

public class Answer {

    Long id;
    Long questionId;
    String text;
    int isCorrect;


    public Answer() {

    }
    public Answer(Long questionId, String text, int isCorrect) {
        this.questionId = questionId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCorrect() {
        return isCorrect;
    }

    public void setCorrect(int correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
