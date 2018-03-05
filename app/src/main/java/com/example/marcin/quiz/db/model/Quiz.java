package com.example.marcin.quiz.db.model;

/**
 * Created by Marcin on 28.02.2018.
 */

public class Quiz {

    Long id;
    String title;
    String type;
    Integer countOfQuestions;
    Integer countOfCorrectAnswers;
    Integer countOfGivenAnswers;

    public Quiz(){

    }

    public Quiz(Long id, String title, String type, Integer countOfQuestions, Integer countOfCorrectAnswers, Integer countOfGivenAnswers) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.countOfQuestions = countOfQuestions;
        this.countOfCorrectAnswers = countOfCorrectAnswers;
        this.countOfGivenAnswers = countOfGivenAnswers;
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

    public Integer getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(Integer countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    public Integer getCountOfCorrectAnswers() {
        return countOfCorrectAnswers;
    }

    public void setCountOfCorrectAnswers(Integer countOfCorrectAnswers) {
        this.countOfCorrectAnswers = countOfCorrectAnswers;
    }

    public Integer getCountOfGivenAnswers() {
        return countOfGivenAnswers;
    }

    public void setCountOfGivenAnswers(Integer countOfGivenAnswers) {
        this.countOfGivenAnswers = countOfGivenAnswers;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", countOfQuestions=" + countOfQuestions +
                ", countOfCorrectAnswers=" + countOfCorrectAnswers +
                ", countOfGivenAnswers=" + countOfGivenAnswers +
                '}';
    }
}
