package com.example.smproject.tasks;

import android.content.Context;
import android.content.Intent;

import com.example.smproject.R;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TasksGenerator {
    private List<String> tasks_text;
    public TasksGenerator(Context context) {
        this.tasks_text= new LinkedList<>(Arrays.asList(context.getResources().getStringArray(R.array.tasks)));
    }

    public SingleTask generateTask(){
        Random random = new Random();
        String taskGoal = tasks_text.get(Math.abs(random.nextInt())%tasks_text.size());
        tasks_text.remove(taskGoal);
        int taskXP =( (Math.abs(random.nextInt())%10)+1)*100;
        int taskDays = (Math.abs(random.nextInt())%14)+1;
        return new SingleTask(taskGoal,taskXP,taskDays);

    }
}
