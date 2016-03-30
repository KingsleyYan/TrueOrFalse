package com.kingyan.android.trueorfalse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String INDEX_KEY = "index";
    private static final String LIST_KEY = "list";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuetionText;
    private boolean mIsCheat;
    private int mCurrentIndex = 0;
    private Question[] questions = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private ArrayList<HashMap<String, Object>> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle savedInstanceState) called");
        setContentView(R.layout.activity_main);

        mQuetionText = (TextView) findViewById(R.id.question_text_view);
        mQuetionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % questions.length;
                updateQuestion();
                mIsCheat = false;
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % questions.length;
                Log.d(TAG, String.valueOf(mCurrentIndex));
                updateQuestion();
                mIsCheat = false;
            }
        });


        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (--mCurrentIndex + questions.length) % questions.length;
                Log.d(TAG, String.valueOf(mCurrentIndex));
                updateQuestion();
                mIsCheat = false;
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheatActivity.class);
                boolean mTrueQuestion = questions[mCurrentIndex].isTrueQuestion();
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mTrueQuestion);
                startActivityForResult(intent, 0);
            }
        });

        if (savedInstanceState != null) {
            Log.d(TAG, savedInstanceState.toString());
            mCurrentIndex = savedInstanceState.getInt(INDEX_KEY, 0);
            mIsCheat = savedInstanceState.getBoolean(CheatActivity.EXTRA_IS_CHEAT, false);
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerQuestion(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerQuestion(false);
            }
        });
        updateQuestion();
    }

    private void updateQuestion() {
        int question = questions[mCurrentIndex].getQuestion();
        mQuetionText.setText(question);
    }

    private void answerQuestion(boolean userPress) {
        boolean answerIsTrue = questions[mCurrentIndex].isTrueQuestion();
        int responseResId;
        Log.d(TAG, "isCheat: " + mIsCheat);
        if (list.isEmpty()) {
            if (mIsCheat) {
                Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
            } else {
                if (answerIsTrue == userPress) {
                    responseResId = R.string.correct_toast;
                } else {
                    responseResId = R.string.incorrect_toast;
                }
                Toast.makeText(this, responseResId, Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (mCurrentIndex == (Integer) list.get(i).get("index")) {
                    Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
                    continue;
                } else {
                    if (answerIsTrue == userPress) {
                        responseResId = R.string.correct_toast;
                    } else {
                        responseResId = R.string.incorrect_toast;
                    }
                    Toast.makeText(this, responseResId, Toast.LENGTH_SHORT).show();
                    continue;
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState called");
        outState.putInt(INDEX_KEY, mCurrentIndex);
        outState.putBoolean(CheatActivity.EXTRA_IS_CHEAT, mIsCheat);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult called");
        if (data == null) {
            return;
        }
        mIsCheat = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOW, false);
        if (mIsCheat) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("index", mCurrentIndex);
            map.put("isCheat", mIsCheat);
            list.add(map);
        }
    }

}
