package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smproject.R;

public class MainActivity extends AppCompatActivity {

    Button tasksButton;
    Button calendarButton;
    Button analyserButton;
    Button statsButton;
    TextView statsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksButton = findViewById(R.id.MA_TasksButton);
        calendarButton = findViewById(R.id.MA_CalendarButton);
        analyserButton = findViewById(R.id.MA_HealthAnalyzerButton);
        statsButton = findViewById(R.id.MA_StatsButton);
        statsView = findViewById(R.id.MA_StatsTextView);

        analyserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAvtivity(HealthAnalyzeActivity.class);
            }
        });

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAvtivity(TasksActivity.class);
            }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statsView.getVisibility()==View.INVISIBLE) {
                    statsView.setVisibility(View.VISIBLE);
                    statsButton.setText(getResources().getString(R.string.hideStatistics));
                    return;
                }
                statsView.setVisibility(View.INVISIBLE);
                statsButton.setText(getResources().getString(R.string.showStatistics));
            }
        });


    }

    private void goToAvtivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }


}