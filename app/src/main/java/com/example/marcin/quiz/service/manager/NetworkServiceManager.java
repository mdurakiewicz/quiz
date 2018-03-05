package com.example.marcin.quiz.service.manager;

import android.content.Context;
import android.util.Log;

import com.example.marcin.quiz.db.AnswerRepository;
import com.example.marcin.quiz.db.QuizRepository;
import com.example.marcin.quiz.db.model.Answer;
import com.example.marcin.quiz.db.model.Question;
import com.example.marcin.quiz.db.model.Quiz;
import com.example.marcin.quiz.db.helper.QuizDBHelper;
import com.example.marcin.quiz.db.repositoryImpl.AnswerRepositoryImpl;
import com.example.marcin.quiz.db.repositoryImpl.QuestionRepositoryImpl;
import com.example.marcin.quiz.db.repositoryImpl.QuizRepositoryImpl;
import com.example.marcin.quiz.service.client.QuizClient;
import com.example.marcin.quiz.service.client.QuizzesClient;
import com.example.marcin.quiz.service.data.quiz.AnswerData;
import com.example.marcin.quiz.service.data.quiz.QuestionData;
import com.example.marcin.quiz.service.data.quiz.QuizDetailsData;
import com.example.marcin.quiz.service.data.quizzes.QuizzesData;
import com.example.marcin.quiz.view.adapter.QuizListAdapter;
import com.example.marcin.quiz.view.data.AnswerViewData;
import com.example.marcin.quiz.view.data.QuestionViewData;
import com.example.marcin.quiz.view.data.QuizViewData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

/**
 * Created by Marcin on 02.03.2018.
 */

public class NetworkServiceManager {

    private static final String TAG = NetworkServiceManager.class.getSimpleName();

    private Subscription quizzesSubscription;
    private List<Subscription> quizDetailsSubscritpionList;
    private static List<QuizViewData> quizViewDataList;
    private static NetworkServiceManager networkServiceManager;

    private NetworkServiceManager() {
        quizDetailsSubscritpionList = new ArrayList<Subscription>();
        quizViewDataList = new ArrayList<QuizViewData>();
    }

    public static NetworkServiceManager getInstance(){
        if(networkServiceManager == null){
            networkServiceManager = new NetworkServiceManager();
        }

        return networkServiceManager;
    }

    public static List<QuizViewData> getQuizViewDataList() {
        return quizViewDataList;
    }

    public Subscription getQuizzesSubscription() {
        return quizzesSubscription;
    }

    public List<Subscription> getQuizDetailsSubscritpionList() {
        return quizDetailsSubscritpionList;
    }

    public void udateQuizViewDataFromDb(QuizDBHelper quizDBHelper){
        quizViewDataList = getQuizViewDataFromDb(quizDBHelper);
    }

    public void getQuizzes(final QuizDBHelper quizDBHelper, final QuizListAdapter quizListAdapter) {
        quizzesSubscription = QuizzesClient.getInstance()
                .getQuizzes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuizzesData>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }
                    @Override public void onError(Throwable e) {Log.d(TAG, "In onError()");}
                    @Override public void onNext(QuizzesData quizzesData) {
                        Log.d(TAG, "In onNext()");


                        /*for(int i = 0; i < 1000000; i++){
                            Log.d(TAG, "7777777777777777777777777777777777777777777777777777)");
                        }*/

                        //quizViewDataList = getQuizViewDataFromDb(quizDBHelper);
                        //quizListAdapter.setQuizList(quizViewDataList);

                        final QuizRepositoryImpl quizRepository = new QuizRepositoryImpl(quizDBHelper);

                        List<Quiz> quizList = getQuizListFromQuizzezData(quizzesData);
                        List<Quiz> savedQuizList = quizRepository.getAll();
                        List<Quiz> differenceQuizList = calculateDifferenceQuizList(savedQuizList, quizList);

                        quizRepository.add(differenceQuizList);

                        quizViewDataList = getQuizViewDataFromDb(quizDBHelper);
                        quizListAdapter.setQuizList(quizViewDataList);


                        Log.d(TAG, "------------------------------");
                        Log.d(TAG, quizViewDataList.toString());
                        Log.d(TAG, "------------------------------");


                        List<Observable<QuizDetailsData>> obsList = new ArrayList<>();
                        for(QuizViewData quizViewData : quizViewDataList){
                            if(quizViewData.getQuestions() == null){
                                long id = quizViewData.getId();
                                Observable<QuizDetailsData> observable = QuizClient.getInstance().getQuizDetails(id).subscribeOn(Schedulers.io());
                                obsList.add(observable);
                            }
                        }

                        Observable z = Observable.zip(obsList, new FuncN() {
                            @Override
                            public List<QuizDetailsData> call(Object... args) {
                                List<QuizDetailsData> quizDetailsDataList = new ArrayList<>();

                                for(Object obj : args){
                                    QuizDetailsData quizDetailsData = (QuizDetailsData) obj;
                                    quizDetailsDataList.add(quizDetailsData);
                                }

                                return quizDetailsDataList;
                            }
                        });

                        Subscription subscription = z.subscribe(new Observer<List<QuizDetailsData>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<QuizDetailsData> quizDetailsDataList) {
                                Log.d(TAG, "Start saving data");

                                saveQuestionAndAnswerDataToDBAndUpdateQuizViewData(quizDBHelper, quizDetailsDataList);

                                QuizRepositoryImpl quizRepository = new QuizRepositoryImpl(quizDBHelper);
                                QuestionRepositoryImpl questionRepository = new QuestionRepositoryImpl(quizDBHelper);
                                AnswerRepositoryImpl answerRepository = new AnswerRepositoryImpl((quizDBHelper));

                                Log.d(TAG, String.valueOf(quizRepository.getAll().size()));
                                Log.d(TAG, String.valueOf(questionRepository.getAll().size()));
                                Log.d(TAG, String.valueOf(answerRepository.getAll().size()));

                                Log.d(TAG, "End saving data");
                            }
                        });

                        quizDetailsSubscritpionList.add(subscription);

                    }

                });
    }

    private void saveQuestionAndAnswerDataToDBAndUpdateQuizViewData(QuizDBHelper quizDBHelper, List<QuizDetailsData> quizDetailsDataList){
        QuestionRepositoryImpl questionRepository = new QuestionRepositoryImpl(quizDBHelper);
        AnswerRepositoryImpl answerRepository = new AnswerRepositoryImpl(quizDBHelper);

        for(QuizDetailsData quizDetailsData : quizDetailsDataList){

            Log.d("Quiz ", String.valueOf(quizDetailsData.getId()));

            List<QuestionViewData> questionViewDataList = new ArrayList<>();

            for(QuestionData questionData : quizDetailsData.getQuestions()){
                Question question = new Question();
                question.setQuizId(quizDetailsData.getId());
                question.setText(questionData.getText());

                long questionId = questionRepository.add(question);

                Log.d("Question ", String.valueOf(questionId));

                QuestionViewData questionViewData = new QuestionViewData();
                questionViewData.setId(questionId);
                questionViewData.setText(questionData.getText());

                List<AnswerViewData> answerViewDataList = new ArrayList<>();

                for(AnswerData answerData : questionData.getAnswers()){
                    Answer answer = new Answer();
                    answer.setQuestionId(questionId);
                    answer.setCorrect(answerData.getCorrect());
                    answer.setText(answerData.getText());

                    long answerId = answerRepository.add(answer);
                    Log.d("Answer ", String.valueOf(answerId));

                    AnswerViewData answerViewData = new AnswerViewData();
                    answerViewData.setId(answerId);
                    answerViewData.setCorrect(answerData.getCorrect());
                    answerViewData.setText(answerData.getText());
                    answerViewDataList.add(answerViewData);
                }

                questionViewData.setAnswers(answerViewDataList);
                questionViewDataList.add(questionViewData);
            }

            for(QuizViewData quizViewData : quizViewDataList){
                if(quizViewData.getId().equals(quizDetailsData.getId())){
                    quizViewData.setQuestions(questionViewDataList);
                }
            }
        }

    }

    private List<QuizViewData> getQuizViewDataFromDb(QuizDBHelper quizDBHelper){
        List<QuizViewData> quizViewDataList = new ArrayList<QuizViewData>();
        List<Quiz> quizList = new ArrayList<Quiz>();
        List<Question> questionList = new ArrayList<Question>();
        List<Answer> answerList = new ArrayList<Answer>();

        QuizRepositoryImpl quizRepository = new QuizRepositoryImpl(quizDBHelper);
        QuestionRepositoryImpl questionRepository = new QuestionRepositoryImpl(quizDBHelper);
        AnswerRepositoryImpl answerRepository = new AnswerRepositoryImpl(quizDBHelper);

        quizList = quizRepository.getAll();
        questionList = questionRepository.getAll();
        answerList = answerRepository.getAll();

        Map <Long, List<AnswerViewData>> answerViewDataMap = new HashMap<>();
        for(Answer answer : answerList){
            AnswerViewData answerViewData = new AnswerViewData();
            answerViewData.setId(answer.getId());
            answerViewData.setCorrect(answer.getCorrect());
            answerViewData.setText(answer.getText());

            List<AnswerViewData> answerViewDataList = answerViewDataMap.get(answer.getQuestionId());
            if(answerViewDataList == null) {
                answerViewDataList = new ArrayList<>();
            }

            answerViewDataList.add(answerViewData);
            answerViewDataMap.put(answer.getQuestionId(), answerViewDataList);
        }

        Map<Long, List<QuestionViewData>> questionViewDataMap = new HashMap<>();
        for(Question question : questionList){
            QuestionViewData questionViewData = new QuestionViewData();
            questionViewData.setId(question.getId());
            questionViewData.setText(question.getText());
            questionViewData.setAnswers(answerViewDataMap.get(question.getId()));

            List<QuestionViewData> questionViewDataList = questionViewDataMap.get(question.getQuizId());
            if(questionViewDataList == null) {
                questionViewDataList = new ArrayList<>();
            }
            questionViewDataList.add(questionViewData);

            questionViewDataMap.put(question.getQuizId(), questionViewDataList);
        }

        for(Quiz quiz : quizList){
            QuizViewData quizViewData = new QuizViewData();
            quizViewData.setId(quiz.getId());
            quizViewData.setTitle(quiz.getTitle());
            quizViewData.setType(quiz.getType());
            quizViewData.setCountOfQuestions(quiz.getCountOfQuestions());
            quizViewData.setCountOfGivenAnswers(quiz.getCountOfGivenAnswers());
            quizViewData.setCountOfCorrectAnswers(quiz.getCountOfCorrectAnswers());

            quizViewData.setQuestions(questionViewDataMap.get(quiz.getId()));

            quizViewDataList.add(quizViewData);
        }

        return quizViewDataList;
    }

    private List<Quiz> getQuizListFromQuizzezData(QuizzesData quizzesData){
        List<Quiz> quizList = new ArrayList<Quiz>();

        for(int i = 0; i < quizzesData.getCount(); i++){
            quizList.add(new Quiz(
                    quizzesData.getItems().get(i).getId(),
                    quizzesData.getItems().get(i).getTitle(),
                    quizzesData.getItems().get(i).getType(),
                    quizzesData.getItems().get(i).getQuestions(),
                    0,
                    0
            ));
        }

        return quizList;
    }

    private List<Quiz> calculateDifferenceQuizList(List<Quiz> savedQuizList, List<Quiz> quizList){
        List <Quiz> differenceQuiz = new ArrayList<Quiz>();

        for(int i = 0; i < quizList.size(); i++){
            boolean isEquals = false;
            for(int j = 0; j < savedQuizList.size(); j++){
                if(quizList.get(i).getId().equals(savedQuizList.get(j).getId())){
                    isEquals = true;
                    break;
                }
            }

            if(!isEquals){
                differenceQuiz.add(quizList.get(i));
            }
        }

        return differenceQuiz;
    }
}
