package com.example.marcin.quiz.view.data;

import java.util.List;

/**
 * Created by Marcin on 02.03.2018.
 */

public class QuizViewData {
    String title;
    String type;
    Long id;
    Integer countOfQuestions;
    Integer countOfCorrectAnswers;
    Integer countOfGivenAnswers;
    List<QuestionViewData> questions;

    public QuizViewData(){

    }

    public QuizViewData(String title, String type, Long id, Integer countOfQuestions, Integer countOfCorrectAnswers, Integer countOfGivenAnswers, List<QuestionViewData> questions) {
        this.title = title;
        this.type = type;
        this.id = id;
        this.countOfQuestions = countOfQuestions;
        this.countOfCorrectAnswers = countOfCorrectAnswers;
        this.countOfGivenAnswers = countOfGivenAnswers;
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

    public List<QuestionViewData> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionViewData> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuizViewData{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", countOfQuestions=" + countOfQuestions +
                ", countOfCorrectAnswers=" + countOfCorrectAnswers +
                ", countOfGivenAnswers=" + countOfGivenAnswers +
                ", questions=" + questions +
                '}';
    }
}
