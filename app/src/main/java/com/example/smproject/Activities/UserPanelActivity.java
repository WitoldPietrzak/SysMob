package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smproject.DatabaseHandler;
import com.example.smproject.R;
import com.example.smproject.User;
import com.example.smproject.tasks.Task;

import java.util.List;

public class UserPanelActivity extends AppCompatActivity {

    TextView userNameView;
    TextView lvlView;
    TextView streakView;
    TextView completedTasksView;
    TextView lastTaskView;
    LinearLayout taskListLayout;

    User user;
    List<Task> userTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        user = (User) getIntent().getSerializableExtra("user");
        if(user == null)
        {
            user = new DatabaseHandler(this).getUser("TestUser");
            return;
        }
        userTasks = user.getCurrentTasks();

        userNameView=findViewById(R.id.UP_UsernameView);
        lvlView=findViewById(R.id.UP_LvlView);
        streakView=findViewById(R.id.UP_StreakView);
        completedTasksView=findViewById(R.id.UP_CompletedTasksView);
        lastTaskView=findViewById(R.id.UP_LastTaskCompletionDateView);
        taskListLayout=findViewById(R.id.UP_TaskListLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (userTasks == null || userTasks.isEmpty())
        {
            return;
        }
        for(Task task : userTasks)
        {
            TextView taskView = new TextView(this);
            taskView.setText(task.getGoal());
            taskListLayout.addView(taskView);

        }

        userNameView.setText(userNameView.getText()+user.getUserName());
    }
}