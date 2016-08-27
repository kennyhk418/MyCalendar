package com.example.android.mycalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyCalendar extends AppCompatActivity {
    static final int REQUEST_CODE = 0;
    static long eventID = 0;
    List<WeekViewEvent> mainEvents = new ArrayList<WeekViewEvent>();
    int sYear, sMonth, sDay, sHour, sMin; //Starting Date
    int eHour, eMin; //Ending Date
    String activity;
    boolean isDatePassed = false;

    private List<WeekViewEvent> getEvents(int year, int month) {
        List<WeekViewEvent> tempList = new ArrayList<>();
        for (WeekViewEvent weekViewEvent : mainEvents) {
            if (weekViewEvent.getStartTime().get(java.util.Calendar.MONTH) == month && weekViewEvent.getStartTime().get(java.util.Calendar.YEAR) ==
                    year) {
                tempList.add(weekViewEvent);
            }
        }
        return tempList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        // Get a reference for the week view in the layout.
        final WeekView mWeekView = (WeekView) findViewById(R.id.weekView);

        mWeekView.goToToday();
        mWeekView.goToHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Calendar startingTime = event.getStartTime();
                Calendar endingTime = event.getEndTime();
                int year, month, day, shour, smin, ehour,emin;
                year = startingTime.get(Calendar.YEAR);
                month = startingTime.get(Calendar.MONTH);
                day = startingTime.get(Calendar.DAY_OF_MONTH);
                shour = startingTime.get(Calendar.HOUR_OF_DAY);
                smin = startingTime.get(Calendar.MINUTE);
                ehour = endingTime.get(Calendar.HOUR_OF_DAY);
                emin = endingTime.get(Calendar.MINUTE);
                String activity = event.getName();
                long eventID = event.getId();
                Intent showingEvent = new Intent(MyCalendar.this, ShowingEvent.class);
                showingEvent.putExtra("year",year).putExtra("month",month).putExtra("day",day)
                        .putExtra("shour",shour).putExtra("smin",smin).putExtra("ehour",ehour)
                        .putExtra("emin",emin).putExtra("activity",activity).putExtra("eventID",eventID);
                startActivity(showingEvent);
            }
        });

        final MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // Populate the week view with some events.
                List<WeekViewEvent> events = getEvents(newYear, newMonth);
                /*java.util.Calendar startTime = java.util.Calendar.getInstance();
                java.util.Calendar endTime = (java.util.Calendar) startTime.clone();
                endTime.add(java.util.Calendar.HOUR, 1);
                WeekViewEvent newEvent = new WeekViewEvent(1, "New Event", startTime, endTime);
                mainEvents.add(newEvent);*/
                return events;
            }
        };

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            //When the user click on an empty event, it will proceed to an AddingEvent Page
            @Override
            public void onEmptyViewClicked(java.util.Calendar time) {
                Intent addingEvent = new Intent(MyCalendar.this, AddingEvent.class);
                startActivityForResult(addingEvent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //When user comes back from the AddingEvent page, put the event into the calendar
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // So it successfully passes the data from the intent to the mainactivity
            activity = data.getStringExtra("activity");
            sYear = data.getIntExtra("sYear", -1);
            sMonth = data.getIntExtra("sMonth", -1);
            sDay = data.getIntExtra("sDay", -1);
            sHour = data.getIntExtra("sHour", -1);
            sMin = data.getIntExtra("sMin", -1);
            eHour = data.getIntExtra("eHour", -1);
            eMin = data.getIntExtra("eMin", -1);
            isDatePassed = data.getBooleanExtra("isDatePassed", false);
            if (isDatePassed) {
                final WeekView mWeekView = (WeekView) findViewById(R.id.weekView);
                Calendar startingTime = Calendar.getInstance();
                Calendar endingTime = Calendar.getInstance();
                startingTime.set(Calendar.YEAR, sYear);
                startingTime.set(Calendar.MONTH, sMonth);
                startingTime.set(Calendar.DAY_OF_MONTH, sDay);
                endingTime.set(Calendar.YEAR, sYear);
                endingTime.set(Calendar.MONTH, sMonth);
                endingTime.set(Calendar.DAY_OF_MONTH, sDay);
                startingTime.set(Calendar.HOUR_OF_DAY, sHour);
                startingTime.set(Calendar.MINUTE, sMin);
                endingTime.set(Calendar.HOUR_OF_DAY, eHour);
                endingTime.set(Calendar.MINUTE, eMin);
                WeekViewEvent newEvent = new WeekViewEvent(eventID, activity, startingTime, endingTime);
                eventID++;
                Toast.makeText(MyCalendar.this, "Successfully Added An Event", Toast.LENGTH_SHORT).show();
                mainEvents.add(newEvent);
                mWeekView.notifyDatasetChanged();
            }
        }
    }
}
