package com.example.smproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarView extends LinearLayout {
    // calendar components
    LinearLayout header;
    TextView txtDateDay;
    TextView txtDisplayDate;
    TextView txtDateYear;
    GridView gridView;
    Button startIsolationButton;
    Button stopIsolationButton;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById(R.id.calendar_header);
        txtDateDay = findViewById(R.id.date_display_day);
        txtDateYear = findViewById(R.id.date_display_year);
        txtDisplayDate = findViewById(R.id.date_display_date);
        gridView = findViewById(R.id.calendar_grid);
        startIsolationButton = findViewById(R.id.CL_startIsolationButton);
        stopIsolationButton = findViewById(R.id.CL_stopIsolationButton);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_layout, this);
        assignUiElements();
    }

    public void updateCalendar(Date isolationStart, Date isolationEnd, boolean isOnIsolation) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        currentCalendar.set(Calendar.DAY_OF_MONTH, currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        int monthEndingCell = currentCalendar.get(Calendar.DAY_OF_WEEK) - 2;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        currentCalendar.add(Calendar.DAY_OF_MONTH, (7 - monthEndingCell) % 7);

        // fill cells
        while (calendar.getTimeInMillis() < currentCalendar.getTimeInMillis()) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        gridView.setAdapter(new CalendarAdapter(getContext(), cells, isolationStart, isolationEnd));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,d MMM,yyyy");
        String[] dateToday = sdf.format(Calendar.getInstance().getTime()).split(",");
        txtDateDay.setText(dateToday[0]);
        txtDisplayDate.setText(dateToday[1]);
        txtDateYear.setText(dateToday[2]);

        startIsolationButton.setEnabled(!isOnIsolation);
        stopIsolationButton.setEnabled(isOnIsolation);


    }

    public void setStartButtonListener(OnClickListener onClickListener) {
        startIsolationButton.setOnClickListener(onClickListener);
    }

    public void setStopButtonListener(OnClickListener onClickListener) {
        stopIsolationButton.setOnClickListener(onClickListener);
    }

}