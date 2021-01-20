package com.example.smproject;

import com.example.smproject.tasks.Task;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
        updateIsolationStatus();
        currentTasks = new LinkedList<>();
        completedTasks = new LinkedList<>();
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

    public Date getIsolationEndDate() {
        return isolationEndDate;
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

    public void updateIsolationStatus() {
        Calendar cal = Calendar.getInstance();
        if (isOnIsolation) {
            if (cal.getTime().after(isolationEndDate)) {
                isOnIsolation = false;

            }
        }
    }
    public void updateStreak()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        if(lastTaskCompletionDate == null ||lastTaskCompletionDate.before(calendar.getTime()))
        {
            dayStreak=0;
        }
    }

    public void stopIsolation(){
        if(isOnIsolation) {
            isolationEndDate = Calendar.getInstance().getTime();
            isOnIsolation= false;
        }
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
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,-2);
            if(lastTaskCompletionDate == null ||lastTaskCompletionDate.after(calendar.getTime()))
            {
                dayStreak++;
            }

            if(lastTaskCompletionDate == null){
                lastTaskCompletionDate = new Date();
            }
            lastTaskCompletionDate.setTime(task.getCompletionDate().getTime());
            this.currentTasks.remove(task);
        }
    }

    public void giveUp(Task task)
    {
        if(this.currentTasks.contains(task)) {
            task.giveUp();
            this.currentTasks.remove(task);
        }
    }

}
