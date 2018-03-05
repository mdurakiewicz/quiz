package com.example.marcin.quiz.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcin.quiz.R;
import com.example.marcin.quiz.view.data.QuizViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 02.03.2018.
 */

public class QuizListAdapter extends BaseAdapter{

    private List<QuizViewData> quizViewDataList = new ArrayList<QuizViewData>();
    private static LayoutInflater inflater=null;

    public QuizListAdapter(Context context) {
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quizViewDataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < 0 || position >= quizViewDataList.size()) {
            return null;
        } else {
            return quizViewDataList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.row_quiz, null);
        TextView txtViewTitle = rowView.findViewById(R.id.title);
        TextView txtViewDetails = rowView.findViewById(R.id.details);

        txtViewTitle.setText(quizViewDataList.get(position).getTitle());

        int correctAnsw = quizViewDataList.get(position).getCountOfCorrectAnswers();
        int countOfQuestions = quizViewDataList.get(position).getCountOfQuestions();
        int givenAnsw = quizViewDataList.get(position).getCountOfGivenAnswers();

        if(countOfQuestions > 0 && countOfQuestions != givenAnsw){
            int percent = (int) Math.floor((givenAnsw * 100) / countOfQuestions);
            txtViewDetails.setText("Quiz rozwiązany w " + String.valueOf(percent) + "%");
        } else if(countOfQuestions > 0){
            int percent = (int) Math.floor((correctAnsw * 100) / countOfQuestions);
            txtViewDetails.setText("Ostatni wynik: " + String.valueOf(correctAnsw) + "/" + String.valueOf(countOfQuestions) + " "
                    + String.valueOf(percent) + "%");
        } else {
            txtViewDetails.setText("Quiz nie ma pytań ("+ countOfQuestions + ")");
        }

        return rowView;
    }

    public void setQuizList(@Nullable List<QuizViewData> quizViewDataList) {
        if (quizViewDataList == null) {
            return;
        }

        this.quizViewDataList.clear();
        this.quizViewDataList.addAll(quizViewDataList);
        notifyDataSetChanged();
    }


}
