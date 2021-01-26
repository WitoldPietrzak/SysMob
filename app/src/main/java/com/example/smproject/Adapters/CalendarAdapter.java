package com.example.smproject.Adapters;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smproject.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarAdapter extends ArrayAdapter<Date> {

    private LayoutInflater inflater;
    private Date isolationStart;
    private Date isolationEnd;

    public CalendarAdapter(Context context, ArrayList<Date> days, Date isolationStart, Date isolationEnd) {
        super(context, R.layout.custom_calendar_day, days);
        this.isolationStart = isolationStart;
        this.isolationEnd = isolationEnd;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // day in question
        Calendar calendar = Calendar.getInstance();
        Date date = getItem(position);
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);


        if (view == null)
            view = inflater.inflate(R.layout.custom_calendar_day, parent, false);

        ((TextView) view).setTypeface(null, Typeface.NORMAL);

        if (month != calendarToday.get(Calendar.MONTH) || year != calendarToday.get(Calendar.YEAR)) {

            ((TextView) view).setTextColor(getContext().getColor(R.color.darkerTextColor));
        }
        if (isolationStart != null && isolationEnd != null) {
            if (date.compareTo(isolationEnd) <= 0 && date.compareTo(isolationStart) >= 0) {
                ((TextView) view).setTextColor(Color.RED);
            }
        }
        if (day == calendarToday.get(Calendar.DATE)) {


            ((TextView) view).setGravity(Gravity.CENTER);
            view.setBackgroundResource(R.drawable.background_circle_calendar);
        }


        ((TextView) view).setText(String.valueOf(calendar.get(Calendar.DATE)));

        return view;
    }
}
