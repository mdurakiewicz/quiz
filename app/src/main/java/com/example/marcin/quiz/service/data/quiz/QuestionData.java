package com.example.marcin.quiz.service.data.quiz;

import java.util.List;

/**
 * Created by Marcin on 01.03.2018.
 */

public class QuestionData {

    String text;
    List<AnswerData> answers;

    public QuestionData() {
    }

    public QuestionData(String text, List<AnswerData> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AnswerData> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerData> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionData{" +
                ", text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }
}
