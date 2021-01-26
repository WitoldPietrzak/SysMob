package com.example.smproject.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smproject.R;
import com.example.smproject.tasks.Task;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class TasksTakeView extends androidx.appcompat.widget.LinearLayoutCompat {

    TextView taskNameView;
    TextView XPPointsView;
    TextView timeView;
    Button takeButton;
    Task task;

    public void loadTask(Task task){
        this.task = task;

        taskNameView.setText(task.getGoal());
        XPPointsView.setText(String.valueOf(task.getExperience()) + " XP");
        timeView.setText((int) TimeUnit.MILLISECONDS.toDays(task.getRemainingTimeInMillis())+1+" Days");
       updateUI();

    }

    public void updateUI()
    {
        if (task.isPicked())
        {
            takeButton.setText("Task taken");
            takeButton.setEnabled(false);
        }
        else {
            takeButton.setText("Take Task");
            takeButton.setEnabled(true);
        }
    }


    private void assignUiElements() {
        taskNameView = findViewById(R.id.TSTI_taskNameView);
        XPPointsView = findViewById(R.id.TSTI_taskXPView);
        takeButton = findViewById(R.id.TSTI_taskTakeButton);
        timeView = findViewById(R.id.TSTI_timeInDaysView);


    }

    public void assignButtonAction(OnClickListener onClickListener){
        takeButton.setOnClickListener(onClickListener);
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tasks_simple_task_info, this);
        assignUiElements();
    }


    public TasksTakeView(@NonNull Context context) {
        super(context);
        initControl(context);
    }

    public TasksTakeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public TasksTakeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }
}
