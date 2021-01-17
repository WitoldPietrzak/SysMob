package com.example.smproject.tasks;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TasksManager implements Serializable {
    private List<SingleTask> singleTasks;
    private List<StepTask> stepTasks;
    private List<ProgressiveTask> progressiveTasks;

    public TasksManager(List<Task> existingTasks) {
        singleTasks = new LinkedList<>();
        stepTasks = new LinkedList<>();
        progressiveTasks = new LinkedList<>();
        updateTasks(existingTasks);
    }

    public SingleTask getDailySingleTask()
    {
        Calendar calendar = Calendar.getInstance();
        long time = TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis());
        return singleTasks.get((int) (time%singleTasks.size()));
    }
    public ProgressiveTask getDailyProgressiveTask()
    {
        Calendar calendar = Calendar.getInstance();
        long time = TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis());
        return progressiveTasks.get((int) (time%progressiveTasks.size()));
    }
    public StepTask getDailyStepTask()
    {
        Calendar calendar = Calendar.getInstance();
        long time = TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis());
        return stepTasks.get((int) (time%stepTasks.size()));
    }

    public List<SingleTask> getSingleTasks() {
        return singleTasks;
    }

    public List<StepTask> getStepTasks() {
        return stepTasks;
    }

    public List<ProgressiveTask> getProgressiveTasks() {
        return progressiveTasks;
    }

    public void setSingleTasks(List<SingleTask> singleTasks) {
        this.singleTasks = singleTasks;
    }

    public void setStepTasks(List<StepTask> stepTasks) {
        this.stepTasks = stepTasks;
    }

    public void setProgressiveTasks(List<ProgressiveTask> progressiveTasks) {
        this.progressiveTasks = progressiveTasks;
    }

    public void updateTasks(List<Task> existingTasks)
    {
        if(existingTasks == null){
            return;
        }
        List<Task> tasks = new LinkedList<>();
        tasks.addAll(singleTasks);
        tasks.addAll(stepTasks);
        tasks.addAll(progressiveTasks);
        for(Task task : tasks){
            for(Task existingTask : existingTasks)
            {
                if( task.getTaskID() == existingTask.getTaskID())
                {
                    task = existingTask;
                }
            }
        }
    }

    public String Serialize()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public TasksManager Deserialize(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json,this.getClass());
    }
}
