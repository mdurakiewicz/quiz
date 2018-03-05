package com.example.marcin.quiz.view.data;

/**
 * Created by Marcin on 02.03.2018.
 */

public class AnswerViewData {

    Long id;
    String text;
    int isCorrect;

    public  AnswerViewData(){

    }

    public AnswerViewData(Long id, String text, int isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
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

    public int getCorrect() {
        return isCorrect;
    }

    public void setCorrect(int correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "AnswerViewData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
