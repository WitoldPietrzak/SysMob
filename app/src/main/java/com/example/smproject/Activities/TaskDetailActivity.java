package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smproject.DatabaseHandler;
import com.example.smproject.R;
import com.example.smproject.User;
import com.example.smproject.tasks.Task;

import java.util.UUID;

public class TaskDetailActivity extends AppCompatActivity {
    UUID uuid;
    User user;
    Task task;

    TextView taskName;
    TextView taskID;
    TextView taskXP;
    TextView taskBegin;
    TextView taskEnd;
    TextView taskIsActive;
    TextView taskIsCompleted;
    TextView userLevel;
    Button finishButton;
    Button giveUpButton;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        uuid = (UUID) getIntent().getSerializableExtra("ID");
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        databaseHandler.close();
        for (Task task : user.getCurrentTasks()) {
            if (task.getTaskID().toString().equals(uuid.toString())) {
                this.task = task;
            }
        }
        if (task == null) {
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskName = findViewById(R.id.ATD_taskName);
        taskID = findViewById(R.id.ATD_taskIDValue);
        taskXP = findViewById(R.id.ATD_taskXPValue);
        taskBegin = findViewById(R.id.ATD_BeginDateValue);
        taskEnd = findViewById(R.id.ATD_EndDateValue);
        finishButton = findViewById(R.id.ATD_FinishTaskButton);
        giveUpButton = findViewById(R.id.ATD_GiveUpButton);
        taskIsActive = findViewById(R.id.ATD_isTaskActive);
        taskIsCompleted = findViewById(R.id.ATD_isTaskCompleted);
        progressBar = findViewById(R.id.ATD_progressBar);
        userLevel = findViewById(R.id.ATD_userLevel);

        updateText();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.completeTask(task);
                progressBarAnimation();
                updateText();
            }
        });
        giveUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.giveUp(task);
                updateText();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUserData();
    }

    protected void saveUserData() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.updateUser(user);
        databaseHandler.close();
    }

    public void updateText() {
        taskName.setText(task.getGoal());
        taskID.setText(task.getTaskID().toString());
        taskXP.setText(String.valueOf(task.getExperience()));
        taskBegin.setText(task.getStartDate().toString());
        taskEnd.setText(task.getEndDate().toString());
        taskIsActive.setText(String.valueOf(task.isPicked()));
        taskIsCompleted.setText(String.valueOf(task.isCompleted()));
            finishButton.setEnabled(task.isPicked());
            giveUpButton.setEnabled(task.isPicked());
        progressBar.setProgress((int) (user.getExperience()*100/user.getExperienceCap()),false);
        userLevel.setText(String.valueOf(user.getLevel()));

    }

    public void progressBarAnimation()
    {
        progressBar.setProgress((int) (user.getExperience()*100/user.getExperienceCap()),true);


    }
}