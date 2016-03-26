package com.kingyan.android.trueorfalse;

/**
 * Created by yanj on 16/03/26.
 */
public class Question {
    private int mQuestion;
    private boolean mTrueQuestion;

    public Question(int question, boolean trueQuestion) {
        mQuestion = question;
        mTrueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
