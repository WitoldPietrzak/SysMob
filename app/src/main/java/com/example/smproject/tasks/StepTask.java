package com.example.smproject.tasks;

import java.util.LinkedList;
import java.util.List;

public class StepTask extends Task {
    protected int stepsAmount;
    protected int currentStep;
    List<String> stepGoals = new LinkedList<String>();

    public StepTask(String goal, int daysTimeLimit, int stepsAmount, LinkedList<String> stepGoals) throws Exception {
        super(goal, daysTimeLimit);
        this.stepsAmount = stepsAmount;
        this.currentStep = 1;
        if (stepGoals.size() != stepsAmount) {
            throw new Exception("Wrong stepGoals size, expected: " + stepsAmount + ", got: " + stepGoals.size() + ".");
        }
        this.stepGoals = stepGoals;
    }


    public long getPercentageOfCompletion()
    {
        if(isCompleted())
        {
            return 100;
        }
        return ((currentStep-1)*100/stepsAmount);
    }
    public int getStepsAmount() {
        return stepsAmount;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public List<String> getStepGoals() {
        return stepGoals;
    }

    public void  nextStep()
    {
        currentStep++;
    }

    @Override
    public String toString() {
        return "StepTask{" +
                "stepsAmount=" + stepsAmount +
                ", currentStep=" + currentStep +
                ", stepGoals=" + stepGoals +
                ", goal='" + goal + '\'' +
                ", picked=" + picked +
                ", completed=" + completed +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                '}';
    }
}
