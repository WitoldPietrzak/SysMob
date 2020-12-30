package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smproject.DatabaseHandler;
import com.example.smproject.R;
import com.example.smproject.StatsManager;
import com.example.smproject.User;

import org.json.JSONException;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    Button tasksButton;
    Button calendarButton;
    Button analyserButton;
    Button statsButton;
    TextView statsView;
    TextView statsRegion;
    TextView lvlView;
    TextView streakView;
    TextView expView;
    ProgressBar progressBar;
    StatsManager statsManager;


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            statsManager = new StatsManager(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tasksButton = findViewById(R.id.MA_TasksButton);
        calendarButton = findViewById(R.id.MA_CalendarButton);
        analyserButton = findViewById(R.id.MA_HealthAnalyzerButton);
        statsButton = findViewById(R.id.MA_StatsButton);
        statsView = findViewById(R.id.MA_StatsTextView);
        statsRegion = findViewById(R.id.MA_statsRegion);
        lvlView = findViewById(R.id.MA_levelView);
        expView = findViewById(R.id.MA_expView);
        streakView = findViewById(R.id.MA_streakView);
        progressBar = findViewById(R.id.MA_progressBar);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        if (user == null) {
            user = new User("TestUser");
            databaseHandler.addUser(user);
            databaseHandler.close();
        }

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
                if (statsView.getVisibility() == View.INVISIBLE) {
                    statsView.setVisibility(View.VISIBLE);
                    statsRegion.setVisibility(View.VISIBLE);
                    try {
                        showGlobalStats();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    statsButton.setText(getResources().getString(R.string.hideStatistics));
                    return;
                }
                statsView.setVisibility(View.INVISIBLE);
                statsRegion.setVisibility(View.INVISIBLE);
                statsButton.setText(getResources().getString(R.string.showStatistics));
            }
        });

        statsRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statsRegion.getText().equals(getResources().getString(R.string.global)))  {
                    statsRegion.setText(getResources().getString(R.string.poland));
                    try {
                        showLocalStats();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    statsRegion.setText(getResources().getString(R.string.global));
                    try {
                        showGlobalStats();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateDisplay();


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.updateUser(user);
        databaseHandler.close();
    }

    private void updateDisplay() {
        lvlView.setText(MessageFormat.format(getResources().getString(R.string.levelDisplay), user.getLevel()));
        expView.setText(MessageFormat.format(getResources().getString(R.string.experienceDisplay), user.getExperience(), user.getExperienceCap()));
        streakView.setText(MessageFormat.format(getResources().getString(R.string.daysDisplay),user.getDayStreak()));
    }

    private void goToAvtivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void showGlobalStats() throws JSONException {
        statsView.setText(statsManager.getStatsGlobal());
        statsRegion.setText("Global");
    }

    private void showLocalStats() throws JSONException {
        statsView.setText(statsManager.getStatsPoland());
    }


}