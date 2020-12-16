package com.example.smproject;

import com.example.smproject.tasks.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String userName;
    private int dayStreak;
    private int totalTasksCompleted;
    private int level;
    private long experience;
    private boolean isOnIsolation;
    private List<Task> currentTasks;
    private List<Task> completedTasks;
    private Date lastTaskCompletionDate;
    private Date isolationStartDate;

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
}
