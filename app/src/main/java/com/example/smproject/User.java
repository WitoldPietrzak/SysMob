package com.example.smproject;

import com.example.smproject.tasks.Task;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String userName;
    private int dayStreak;
    private int totalTasksCompleted;
    private int level =1;
    private long experience;
    private boolean isOnIsolation;
    private List<Task> currentTasks;
    private List<Task> completedTasks;
    private Date lastTaskCompletionDate;
    private Date isolationStartDate;
    private Date isolationEndDate;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getDayStreak() {
        return dayStreak;
    }

    public int getTotalTasksCompleted() {
        return totalTasksCompleted;
    }

    public int getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }

    public long getExperienceCap() {
        return level * 250;
    }

    public boolean isOnIsolation() {
        return isOnIsolation;
    }

    public List<Task> getCurrentTasks() {
        return currentTasks;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public Date getLastTaskCompletionDate() {
        return lastTaskCompletionDate;
    }

    public Date getIsolationStartDate() {
        return isolationStartDate;
    }

    public void addXP(long experience) {
        this.experience += experience;
        while (this.experience > (level * 500) / 2) {
            this.experience -= (level * 500) / 2;
            level++;
        }
    }

    public void addDayStreak() {
        dayStreak++;
    }

    public void startIsolation(int length) {
        Calendar cal = Calendar.getInstance();
        isOnIsolation = true;
        isolationStartDate = cal.getTime();
        cal.add(Calendar.DATE, length);
        isolationEndDate = cal.getTime();
    }

    public void addTask(Task task){
        this.currentTasks.add(task);
    }

    public void completeTask(Task task){
        if(this.currentTasks.contains(task)) {
            this.completedTasks.add(task);
            addXP(task.getExperience());
            totalTasksCompleted++;
            task.complete();
            //TODO day streak update
            lastTaskCompletionDate.setTime(task.getCompletionDate().getTime());
            this.currentTasks.remove(task);
        }
    }
}
