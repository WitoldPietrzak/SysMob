package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smproject.BreathChecker;
import com.example.smproject.HealthAnalizer;
import com.example.smproject.R;

public class HealthAnalyzeActivity extends AppCompatActivity {
    Button startButton;
    Button yesButton;
    Button noButton;
    TextView textView;
    HealthAnalizer healthAnalizer;
    BreathChecker breathChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_analyze);

        healthAnalizer = new HealthAnalizer(this);
        breathChecker = new BreathChecker();

        startButton=findViewById(R.id.HA_StartButton);
        yesButton=findViewById(R.id.HA_YesButton);
        noButton=findViewById(R.id.HA_NoButton);
        textView=findViewById(R.id.HA_titleTextView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breathChecker.Start();
                textView.setText(R.string.breath);
                startButton.setText(R.string.stop);
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        breathChecker.Stop();
                        textView.setText(healthAnalizer.getCurrentQuestion());
                        startButton.setVisibility(View.INVISIBLE);
                        yesButton.setVisibility(View.VISIBLE);
                        noButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthAnalizer.sendAnswer(true);
                if(healthAnalizer.getCurrentQuestion()==null)
                {
                    endAnalise();
                    return;
                }
                textView.setText(healthAnalizer.getCurrentQuestion());
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthAnalizer.sendAnswer(false);
                if(healthAnalizer.getCurrentQuestion()==null)
                {
                    endAnalise();
                    return;
                }
                textView.setText(healthAnalizer.getCurrentQuestion());
            }
        });

    }
    private void endAnalise()
    {
        textView.setText(healthAnalizer.analyzeResults(breathChecker.hasCovid()));
        yesButton.setVisibility(View.INVISIBLE);
        noButton.setVisibility(View.INVISIBLE);
    }
}