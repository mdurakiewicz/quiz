package com.example.marcin.quiz.service.data.quiz;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcin on 01.03.2018.
 */

public class AnswerData {

    String text;

    @SerializedName("isCorrect")
    int isCorrect;

    public AnswerData() {
    }

    public AnswerData(String text, int isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
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

    public void setCorrect(int is_correct) {
        this.isCorrect = is_correct;
    }

    @Override
    public String toString() {
        return "AnswerData{" +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
