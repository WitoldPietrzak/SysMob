package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smproject.R;
import com.example.smproject.tasks.ProgressiveTask;
import com.example.smproject.tasks.SingleTask;
import com.example.smproject.tasks.StepTask;
import com.example.smproject.tasks.Task;
import com.example.smproject.tasks.TasksManager;

import java.util.Arrays;
import java.util.List;

public class TasksActivity extends AppCompatActivity {
    TasksManager tasksManager;
    List<String> steptaskgoals = Arrays.asList("1.Weź mąkę", "Dodaj trochę przypraw", "Tadaaaaaa!");
    List<Task> dailyTasksPool = Arrays.asList(
            new SingleTask("Wyśpij się!", 100, 15),
            new StepTask("Przygotuj pizzę!", 2000, 4, 3, steptaskgoals),
            new ProgressiveTask("Spędź godzinę na czytaniu książki! ", 100, 2, 60, "minut"));
    TextView task1;
    TextView task2;
    TextView task3;
    TextView rtime1;
    TextView rtime2;
    TextView rtime3;
    Button button1;
    Button button2;
    Button button3;

    public TasksActivity() throws Exception {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        tasksManager = (TasksManager) getIntent().getSerializableExtra("tasksManager");
        tasksManager.getSingleTasks().add((SingleTask) dailyTasksPool.get(0));
        tasksManager.getProgressiveTasks().add((ProgressiveTask)dailyTasksPool.get(2));
        tasksManager.getStepTasks().add((StepTask)dailyTasksPool.get(1));

        task1 = findViewById(R.id.TM_DailyTask1);
        task2 = findViewById(R.id.TM_DailyTask2);
        task3 = findViewById(R.id.TM_DailyTask3);
        rtime1 = findViewById(R.id.TM_remaining1);
        rtime2 = findViewById(R.id.TM_remaining2);
        rtime3 = findViewById(R.id.TM_remaining3);
       button1=findViewById(R.id.TM_Task1Button);
        button2=findViewById(R.id.TM_Task2Button);
        button3=findViewById(R.id.TM_Task3Button);

        task1.setText(dailyTasksPool.get(0).getGoal() + "\n XP: " + dailyTasksPool.get(0).getExperience());
        task2.setText(dailyTasksPool.get(1).getGoal() + "\n XP: " + dailyTasksPool.get(1).getExperience());
        task3.setText(dailyTasksPool.get(2).getGoal() + "\n XP: " + dailyTasksPool.get(2).getExperience());
        rtime1.setText("Remaining time: " + dailyTasksPool.get(0).getRemainingTimeAsString());
        rtime2.setText("Remaining time: " + dailyTasksPool.get(1).getRemainingTimeAsString());
        //rtime3.setText("Remaining time: " + dailyTasksPool.get(2).getRemainingTimeAsString());
        rtime3.setText(tasksManager.Serialize());
        System.out.println(tasksManager.Serialize());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyTasksPool.get(0).pick();
                button1.setVisibility(View.INVISIBLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyTasksPool.get(1).pick();
                button2.setVisibility(View.INVISIBLE);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyTasksPool.get(2).pick();
                button3.setVisibility(View.INVISIBLE);

            }
        });


        final Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!this.isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!dailyTasksPool.get(0).isPicked()) {
                                    rtime1.setText("Remaining time: " + dailyTasksPool.get(0).getRemainingTimeAsString());
                                }
                                else {
                                    rtime1.setText("Good Luck!");
                                }
                                if(!dailyTasksPool.get(1).isPicked()) {
                                    rtime2.setText("Remaining time: " + dailyTasksPool.get(1).getRemainingTimeAsString());
                                }
                                else {
                                    rtime2.setText("Good Luck!");
                                }
                                if(!dailyTasksPool.get(2).isPicked()) {
                                    //rtime3.setText("Remaining time: " + dailyTasksPool.get(2).getRemainingTimeAsString());
                                }
                                else {
                                    rtime3.setText("Good Luck!");
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
}