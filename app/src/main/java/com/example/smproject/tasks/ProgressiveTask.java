package com.example.smproject.tasks;

public class ProgressiveTask extends Task {
    protected String unit;
    protected long goalValue;
    protected long currentValue;

    public ProgressiveTask(String goal, int daysTimeLimit, long goalValue, String unit) {
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

    public void increaseValue(long increaseValue)
    {
        currentValue+=increaseValue;
    }

    @Override
    public String toString() {
        return "ProgressiveTask{" +
                "unit='" + unit + '\'' +
                ", goalValue=" + goalValue +
                ", currentValue=" + currentValue +
                ", goal='" + goal + '\'' +
                ", picked=" + picked +
                ", completed=" + completed +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                '}';
    }
}
