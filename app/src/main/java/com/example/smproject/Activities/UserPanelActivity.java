package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smproject.Utils.DatabaseHandler;
import com.example.smproject.R;
import com.example.smproject.User;
import com.example.smproject.Views.SimpleTaskView;
import com.example.smproject.tasks.Task;

import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;

public class UserPanelActivity extends AppCompatActivity {


    TextView streakView;
    TextView completedTasksView;
    TextView lastTaskView;
    LinearLayout taskListLayout;

    User user;
    Thread thread;
    List<Task> userTasks;
    List<SimpleTaskView> taskViews = new LinkedList<>();

    DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        databaseHandler.close();
        dateFormat = DateFormat.getDateInstance();


        streakView = findViewById(R.id.UP_Streak);
        completedTasksView = findViewById(R.id.UP_CompletedTasks);
        lastTaskView = findViewById(R.id.UP_LastTask);
        taskListLayout = findViewById(R.id.UP_TaskListLayout);

    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.updateUser(user);
        databaseHandler.close();
        thread.interrupt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        databaseHandler.close();
        taskViews.clear();
        taskListLayout.removeAllViews();
        userTasks = user.getCurrentTasks();
        reloadView();
        for (int i = 0; i < taskViews.size(); i++) {
            Animate(taskViews.get(i), 400 * i);
        }
    }

    public void reloadView() {
        if (userTasks == null) {
            return;
        }

        streakView.setText(String.valueOf(user.getDayStreak()));
        completedTasksView.setText(String.valueOf(user.getTotalTasksCompleted()));
        if(user.getLastTaskCompletionDate() == null){
            lastTaskView.setText(R.string.never);
        }
        else {
            lastTaskView.setText(dateFormat.format(user.getLastTaskCompletionDate()));
            //lastTaskView.setText(user.getLastTaskCompletionDate().toString());
        }

        for (final Task task : userTasks) {
            SimpleTaskView taskView = new SimpleTaskView(this);
            taskView.loadTask(task);
            taskListLayout.addView(taskView);
            taskViews.add(taskView);

            taskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                    intent.putExtra("ID", task.getTaskID());
                    startActivity(intent);
                }
            });


        }
        if(userTasks.isEmpty())
        {
            TextView textView = new TextView(this);
            textView.setText(R.string.no_tasks);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setPadding(0,350,0,0);
            textView.setTextColor(getResources().getColor(R.color.darkerTextColor));
            taskListLayout.addView(textView);
        }
        thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!this.isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (SimpleTaskView taskView : taskViews) {
                                    taskView.updateTime();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }

    private void Animate(View view, long offset) {
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(400);
        animation.setStartOffset(offset);
        view.setAnimation(animation);
    }
}