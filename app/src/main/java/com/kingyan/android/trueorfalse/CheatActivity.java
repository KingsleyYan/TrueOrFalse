package com.kingyan.android.trueorfalse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.kingyan.android.trueorfalse.MainActivity.extra_answer_is_true";
    public static final String EXTRA_ANSWER_SHOW = "com.kingyan.android.trueorfalse.MainActivity.extra_answer_show";
    public static final String EXTRA_IS_CHEAT = "com.kingyan.android.trueorfalse.MainActivity.extra_is_cheat";
    private static final String TAG = "CheatActivity";
    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAskAnswerTextView;
    private boolean mIsCheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        setAnswerShownResult(false);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAskAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAskAnswerTextView.setText(R.string.true_button);
                } else {
                    mAskAnswerTextView.setText(R.string.false_button);
                }
                mIsCheat = true;
                setAnswerShownResult(mIsCheat);
            }
        });

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOW, false);
            if (mAnswerIsTrue) {
                mAskAnswerTextView.setText(R.string.true_button);
            } else {
                mAskAnswerTextView.setText(R.string.false_button);
            }
            mIsCheat = savedInstanceState.getBoolean(EXTRA_IS_CHEAT, false);
            setAnswerShownResult(mIsCheat);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShow) {
        Log.d(TAG, "setAnswerShownResult called");
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOW, isAnswerShow);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState called");
        outState.putBoolean(EXTRA_ANSWER_SHOW, mAnswerIsTrue);
        outState.putBoolean(EXTRA_IS_CHEAT, mIsCheat);
    }
}
