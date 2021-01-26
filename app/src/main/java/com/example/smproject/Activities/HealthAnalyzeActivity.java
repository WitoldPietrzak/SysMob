package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.smproject.Utils.BreathChecker;
import com.example.smproject.Utils.HealthAnalizer;
import com.example.smproject.R;

import java.text.MessageFormat;

public class HealthAnalyzeActivity extends AppCompatActivity {
    Button startButton;
    Button yesButton;
    Button noButton;
    TextView textView;
    TextView questionCounter;
    HealthAnalizer healthAnalizer;
    BreathChecker breathChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_analyze);

        healthAnalizer = new HealthAnalizer(this);
        breathChecker = new BreathChecker();

        startButton = findViewById(R.id.HA_StartButton);
        yesButton = findViewById(R.id.HA_YesButton);
        noButton = findViewById(R.id.HA_NoButton);
        textView = findViewById(R.id.HA_titleTextView);
        questionCounter = findViewById(R.id.HA_QuestionCounterView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breathChecker.Start();
                //textView.setText(R.string.breath);
                updateText(getResources().getString(R.string.breath));
                startButton.setText(R.string.stop);
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        breathChecker.Stop();
                        //textView.setText(healthAnalizer.getCurrentQuestion());
                        updateText(healthAnalizer.getCurrentQuestion());
                        startButton.setVisibility(View.INVISIBLE);
                        questionCounter.setVisibility(View.VISIBLE);
                        questionCounter.setText(MessageFormat.format(getResources().getString(R.string.question), healthAnalizer.getIterator() + 1, healthAnalizer.getQuestionCount()));
                        yesButton.setVisibility(View.VISIBLE);
                        noButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAnswer(true);
            }
        });


        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAnswer(false);
            }
        });

    }

    private void endAnalise() {
        updateText(healthAnalizer.analyzeResults(breathChecker.hasCovid()));
        yesButton.setVisibility(View.INVISIBLE);
        noButton.setVisibility(View.INVISIBLE);
        questionCounter.setVisibility(View.INVISIBLE);
    }

    private void sendAnswer(boolean answer) {
        healthAnalizer.sendAnswer(answer);
        if (healthAnalizer.getCurrentQuestion() == null) {
            endAnalise();
            return;
        }
        updateText(healthAnalizer.getCurrentQuestion());
    }

    private void updateText(final String text) {

        questionCounter.setText(MessageFormat.format(getResources().getString(R.string.question), healthAnalizer.getIterator() + 1, healthAnalizer.getQuestionCount()));
        final Animation inAnimation = new AlphaAnimation(0.0f, 1.0f);
        inAnimation.setDuration(500);
        Animation outAnimation = new AlphaAnimation(1.0f, 0.0f);
        outAnimation.setDuration(500);
        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(text);
                textView.startAnimation(inAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(outAnimation);
    }
}