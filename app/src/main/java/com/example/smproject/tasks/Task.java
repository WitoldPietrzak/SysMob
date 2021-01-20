package com.example.smproject.tasks;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class Task implements Serializable {
    protected UUID taskID;
    protected String goal;
    protected long experience;
    protected boolean picked;
    protected boolean completed;
    protected Date startDate;
    protected Date endDate;
    protected Date completionDate;

    public Task(String goal, long experience, int daysTimeLimit) {
        Calendar cal = Calendar.getInstance();
        this.goal = goal;
        this.picked = false;
        this.startDate = cal.getTime();
        cal.add(Calendar.DATE, daysTimeLimit);
        this.endDate = cal.getTime();
        this.completed = false;
        this.taskID = UUID.randomUUID();
        this.experience = experience;
    }

    public void pick() {
        picked = true;
    }

    public boolean isAvailable() {
        return Calendar.getInstance().getTime().before(endDate);
    }

    public long getRemainingTimeInMillis() {
        return endDate.getTime() - System.currentTimeMillis();
    }

    public String getRemainingTimeAsString() {
        long timeInMillis = getRemainingTimeInMillis();
        long days = TimeUnit.MILLISECONDS.toDays(timeInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis);
        return String.format(Locale.getDefault(), " %02d days %02d h,%02d min, %02d sec",
                days,
                hours - TimeUnit.DAYS.toHours(days),
                minutes - TimeUnit.HOURS.toMinutes(hours),
                seconds - TimeUnit.MINUTES.toSeconds(minutes)
        );
    }

    public void complete() {
        completed = true;
        picked = false;
        completionDate = Calendar.getInstance().getTime();
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getGoal() {
        return goal;
    }

    public long getExperience() {
        return experience;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public boolean isPicked() {
        return picked;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public UUID getTaskID() {
        return taskID;
    }

    public void giveUp() {
        picked = false;
    }

    @Override
    public String toString() {
        return "Task{" +
                "goal='" + goal + '\'' +
                ", picked=" + picked +
                ", completed=" + completed +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                '}';
    }

    public String Serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
