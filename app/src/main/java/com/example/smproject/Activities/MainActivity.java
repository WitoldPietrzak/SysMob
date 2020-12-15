package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smproject.R;

public class MainActivity extends AppCompatActivity {

    Button tasksButton;
    Button calendarButton;
    Button analyserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksButton = findViewById(R.id.MA_TasksButton);
        calendarButton = findViewById(R.id.MA_CalendarButton);
        analyserButton = findViewById(R.id.MA_HealthAnalyzerButton);

        analyserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAvtivity(HealthAnalyzeActivity.class);
            }
        });


    }

    private void goToAvtivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }


}