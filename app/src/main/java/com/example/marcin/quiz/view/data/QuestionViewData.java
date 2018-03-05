package com.example.marcin.quiz.view.data;

import java.util.List;

/**
 * Created by Marcin on 02.03.2018.
 */

public class QuestionViewData {

    Long id;
    String text;
    List<AnswerViewData> answers;

    public QuestionViewData(){

    }

    public QuestionViewData(Long id, String text, List<AnswerViewData> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AnswerViewData> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerViewData> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionViewData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }
}
