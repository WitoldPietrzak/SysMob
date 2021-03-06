package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.smproject.Utils.DatabaseHandler;
import com.example.smproject.R;
import com.example.smproject.User;
import com.example.smproject.Views.TasksTakeView;
import com.example.smproject.tasks.Task;
import com.example.smproject.tasks.TasksGenerator;

import java.util.LinkedList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {
    TasksGenerator tasksGenerator;
    List<Task> dailyTasksPool = new LinkedList<>();
    User user;
    LinearLayout mainLayout;

    public TasksActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        databaseHandler.close();
        tasksGenerator = new TasksGenerator(this);
        for(int i = 0;i < 6; i++){
            dailyTasksPool.add(tasksGenerator.generateTask());
        }



        mainLayout = findViewById(R.id.AT_mainLayout);

        for(final Task task: dailyTasksPool){
            final TasksTakeView tasksTakeView = new TasksTakeView(getApplicationContext());
            tasksTakeView.loadTask(task);
            tasksTakeView.assignButtonAction(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.pick();
                    user.addTask(task);
                    tasksTakeView.updateUI();
                }
            });
            mainLayout.addView(tasksTakeView);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.updateUser(user);
        databaseHandler.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        databaseHandler.close();
    }


}