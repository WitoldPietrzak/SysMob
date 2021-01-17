package com.example.smproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.smproject.CalendarView;
import com.example.smproject.DatabaseHandler;
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
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        user = databaseHandler.getUser("TestUser");
        databaseHandler.close();

        calendarView.updateCalendar(user.getIsolationStartDate(),user.getIsolationEndDate(),user.isOnIsolation());
        calendarView.setStartButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.startIsolation(10);
                calendarView.updateCalendar(user.getIsolationStartDate(),user.getIsolationEndDate(),user.isOnIsolation());
            }
        });

        calendarView.setStopButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.stopIsolation();
                calendarView.updateCalendar(user.getIsolationStartDate(),user.getIsolationEndDate(),user.isOnIsolation());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.updateUser(user);
        databaseHandler.close();
    }
}