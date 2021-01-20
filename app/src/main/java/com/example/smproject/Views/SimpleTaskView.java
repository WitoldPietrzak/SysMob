package com.example.smproject.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smproject.R;
import com.example.smproject.tasks.Task;

public class SimpleTaskView extends androidx.appcompat.widget.LinearLayoutCompat {

    TextView taskNameView;
    TextView XPPointsView;
    TextView timeRemainingView;

    Task task;

    public void loadTask(Task task){
        this.task = task;

        taskNameView.setText(task.getGoal());
        XPPointsView.setText(String.valueOf(task.getExperience()));
        timeRemainingView.setText(task.getRemainingTimeAsString());
    }

    public void updateTime(){
        timeRemainingView.setText(task.getRemainingTimeAsString());
    }

    private void assignUiElements() {
        taskNameView = findViewById(R.id.SSII_taskName);
        XPPointsView = findViewById(R.id.SSII_XPPoints);
        timeRemainingView = findViewById(R.id.SSII_timeRemaining);


    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stats_simple_task_info_item, this);
        assignUiElements();
    }


    public SimpleTaskView(@NonNull Context context) {
        super(context);
        initControl(context);

    }

    public SimpleTaskView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public SimpleTaskView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
