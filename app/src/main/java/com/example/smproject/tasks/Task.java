package com.example.smproject.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class Task {
    protected String goal;
    protected boolean picked;
    protected boolean completed;
    protected Date endDate;
    protected Date startDate;
    protected Date completionDate;

    public Task(String goal, int daysTimeLimit) {
        Calendar cal = Calendar.getInstance();
        this.goal = goal;
        this.picked = false;
        this.startDate = cal.getTime();
        cal.add(Calendar.DATE, daysTimeLimit);
        this.endDate = cal.getTime();
        this.completed=false;
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
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis);
        return String.format(Locale.getDefault(), "%02d h,%02d min, %02d sec",
                hours,
                minutes - TimeUnit.HOURS.toMinutes(hours),
                seconds - TimeUnit.MINUTES.toSeconds(minutes)
        );
    }

    public void complete()
    {
        completed=true;
        completionDate=Calendar.getInstance().getTime();
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getGoal() {
        return goal;
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
}
