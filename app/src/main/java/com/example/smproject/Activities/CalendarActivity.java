package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smproject.CalendarView;
import com.example.smproject.R;
import com.example.smproject.User;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.AC_CalendarView);
        user = (User) getIntent().getSerializableExtra("user");
        user.startIsolation(3);

        calendarView.updateCalendar(user.getIsolationStartDate(),user.getIsolationEndDate());
    }
}