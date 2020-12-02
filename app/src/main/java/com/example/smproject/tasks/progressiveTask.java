package com.example.smproject.tasks;

public class progressiveTask extends Task {
    protected String unit;
    protected long goalValue;
    protected long currentValue;

    public progressiveTask(String goal, int daysTimeLimit, long goalValue, String unit) {
        super(goal, daysTimeLimit);
        this.goalValue = goalValue;
        this.currentValue = 0;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public long getGoalValue() {
        return goalValue;
    }

    public long getCurrentValue() {
        return currentValue;
    }
}
