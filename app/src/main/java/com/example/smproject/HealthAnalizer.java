package com.example.smproject;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

public class HealthAnalizer {
    int positive_symptoms;
    List<String> symptoms_list;
    int iterator;
    Context context;


    public HealthAnalizer(Context context,List<String> symptoms_list) {
        this.symptoms_list = symptoms_list;
        iterator=0;
        positive_symptoms=0;
        this.context=context;
    }

    public HealthAnalizer(Context context) {
        this.symptoms_list= Arrays.asList(context.getResources().getStringArray(R.array.symptoms));
        iterator=0;
        positive_symptoms=0;
        this.context = context;
    }

    public int getIterator(Context context) {
        return iterator;
    }

    public String getCurrentQuestion()
    {
        if(iterator<symptoms_list.size())
        {
            return symptoms_list.get(iterator);
        }
        else return null;
    }

    public void sendAnswer(boolean answer)
    {
        if(answer){
            positive_symptoms++;
        }
        iterator++;
    }



    public int getPositive_symptoms() {
        return positive_symptoms;
    }

    public String analyzeResults(boolean breathingTestResult)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if(positive_symptoms ==0)
        {
            if(!breathingTestResult) {
                stringBuilder.append(context.getResources().getString(R.string.no_symptoms_found));
                return stringBuilder.toString();
            }
            stringBuilder.append(context.getResources().getString(R.string.just_breathing));
            stringBuilder.append(context.getResources().getString(R.string.consider_doctor));

            return stringBuilder.toString();
        }
        if(positive_symptoms<3)
        {
            stringBuilder.append(context.getResources().getString(R.string.some_sympthoms));
        }
        else {
            stringBuilder.append(context.getResources().getString(R.string.a_lot_of_sympthoms));
        }
        if(breathingTestResult)
        {
            stringBuilder.append(context.getResources().getString(R.string.symptoms_and_cough));
            stringBuilder.append(context.getResources().getString(R.string.call_doctor));
            return stringBuilder.toString();
        }
        if(positive_symptoms<3)
        {
            stringBuilder.append(context.getResources().getString(R.string.consider_doctor));
            return stringBuilder.toString();
        }
        stringBuilder.append(context.getResources().getString(R.string.call_doctor));
        return stringBuilder.toString();

    }
}

