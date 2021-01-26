package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smproject.Utils.DatabaseHandler;
import com.example.smproject.R;
import com.example.smproject.Utils.StatsManager;
import com.example.smproject.User;
import com.example.smproject.tasks.TasksManager;

import org.json.JSONException;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    Button tasksButton;
    Button calendarButton;
    Button analyserButton;
    Button statsButton;
    Button userPanelButton;
    TextView statsView;
    Button statsRegion;
    TextView lvlView;
    TextView streakView;
    TextView expView;
    ProgressBar progressBar;
    StatsManager statsManager;
    TasksManager tasksManager;
    MediaPlayer mediaPlayer;


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        statsManager = new StatsManager(this);

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
        userPanelButton = findViewById(R.id.MA_UserPanelButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.klik);


        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        if (user == null) {
            user = new User("TestUser");
            databaseHandler.addUser(user);
        }
        databaseHandler.close();

        tasksManager = new TasksManager(user.getCurrentTasks());
        tasksManager.updateTasks(user.getCompletedTasks());


        analyserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(HealthAnalyzeActivity.class);
            }
        });

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
                intent.putExtra("tasksManager", tasksManager);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statsView.getVisibility() != View.VISIBLE) {
                    //statsView.setVisibility(View.VISIBLE);
                    showStats();
                    showStatsText();
                    statsRegion.setVisibility(View.VISIBLE);
                    try {
                        statsView.setText(statsManager.getStatsGlobal());
                        statsRegion.setText(R.string.global);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    statsButton.setText(getResources().getString(R.string.hideStatistics));
                    return;
                }
                statsView.setVisibility(View.GONE);
                hideStatsText();
                hideStats();
                statsRegion.setVisibility(View.GONE);
                statsButton.setText(getResources().getString(R.string.showStatistics));
            }
        });

        statsRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statsRegion.getText().equals(getResources().getString(R.string.global))) {
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

        userPanelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserPanelActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        updateDisplay();

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        if (user == null) {
            user = new User("TestUser");
            databaseHandler.addUser(user);
        }
        databaseHandler.close();
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
        streakView.setText(MessageFormat.format(getResources().getString(R.string.daysDisplay), user.getDayStreak()));
        progressBar.setProgress((int) (user.getExperience() * 100 / user.getExperienceCap()), true);

    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }


    private void showGlobalStats() throws JSONException {
        updateStatsText(statsManager.getStatsGlobal());
        statsRegion.setText(getResources().getString(R.string.global));
    }

    private void showLocalStats() throws JSONException {
        updateStatsText(statsManager.getStatsPoland());
    }

    private void updateStatsText(final String text) {
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
                statsView.setText(text);
                statsView.startAnimation(inAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        statsView.startAnimation(outAnimation);
    }

    private void showStatsText() {
        final Animation inAnimation = new AlphaAnimation(0.0f, 1.0f);
        inAnimation.setDuration(500);
        statsView.startAnimation(inAnimation);
    }

    private void hideStatsText() {
        Animation outAnimation = new AlphaAnimation(1.0f, 0.0f);
        outAnimation.setDuration(500);
        statsView.setAnimation(outAnimation);
    }

    private void startAnimation() {
        showView(userPanelButton, 0);
        showView(tasksButton, 500);
        showView(analyserButton, 1000);
        showView(calendarButton, 1500);
        showView(statsButton, 2000);

        showView(lvlView, 2500);
        showView(streakView, 2500);
        showView(expView, 2500);
        showView(progressBar, 2500);

    }

    private void showView(View view, long offset) {
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        animation.setStartOffset(offset);
        view.setAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view) {
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(500);
        animation.setStartOffset(0);
        view.setAnimation(animation);
        view.setVisibility(View.GONE);
    }

    private void showStats() {

        hideView(tasksButton);
        hideView(calendarButton);
        hideView(analyserButton);
        hideView(userPanelButton);
        hideView(lvlView);
        hideView(streakView);
        hideView(expView);
        hideView(progressBar);
        showView(statsView, 500);


    }

    private void hideStats() {

        showView(tasksButton, 500);
        showView(calendarButton, 500);
        showView(analyserButton, 500);
        showView(userPanelButton, 500);
        showView(lvlView, 500);
        showView(streakView, 500);
        showView(expView, 500);
        showView(progressBar, 500);
        hideView(statsView);


    }


}