package com.example.android.mycalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

public class MyCalendar extends AppCompatActivity {
    static final int addingEventCode = 0;
    static final int removeEventCode = 1;
    static long eventID = 0;
    List<WeekViewEvent> mainEvents = new ArrayList<WeekViewEvent>();
    int sYear, sMonth, sDay, sHour, sMin; //Starting Date
    int eHour, eMin; //Ending Date
    String activity;


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
                int year, month, day, shour, smin, ehour, emin;
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
                showingEvent.putExtra("year", year).putExtra("month", month).putExtra("day", day)
                        .putExtra("shour", shour).putExtra("smin", smin).putExtra("ehour", ehour)
                        .putExtra("emin", emin).putExtra("activity", activity).putExtra("eventID", eventID);
                startActivityForResult(showingEvent, removeEventCode);
            }
        });

        //Show the calendar view on the screen
        final MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // Populate the week view with some events.
                List<WeekViewEvent> events = getEvents(newYear, newMonth);
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
                //Do Nothing
            }
        });

        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            //When the user click on an empty event, it will proceed to an AddingEvent Page
            @Override
            public void onEmptyViewClicked(java.util.Calendar time) {
                //Start AddingEvent class when there is an empty spot
                Intent addingEvent = new Intent(MyCalendar.this, AddingEvent.class);
                addingEvent.putExtra("currentTime",(Integer)time.get(Calendar.HOUR_OF_DAY));
                addingEvent.putExtra("eventID", eventID);
                startActivityForResult(addingEvent, addingEventCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get a reference for the week view in the layout.
        final WeekView mWeekView = (WeekView) findViewById(R.id.weekView);

        //When user comes back from the AddingEvent page, put the event into the calendar
        if (requestCode == addingEventCode && resultCode == Activity.RESULT_OK) {
            // So it successfully passes the data from the intent to the mainactivity
            activity = data.getStringExtra("activity");
            sYear = data.getIntExtra("sYear", -1);
            sMonth = data.getIntExtra("sMonth", -1);
            sDay = data.getIntExtra("sDay", -1);
            sHour = data.getIntExtra("sHour", -1);
            sMin = data.getIntExtra("sMin", -1);
            eHour = data.getIntExtra("eHour", -1);
            eMin = data.getIntExtra("eMin", -1);

            // Save the data from AddingEvent
            TimeObject startingObj = new TimeObject(sYear, sMonth, sDay, sHour, sMin);
            TimeObject endingObj = new TimeObject(sYear, sMonth, sDay, eHour, eMin);
            startingObj.setCalendar();
            endingObj.setCalendar();
            WeekViewEvent newEvent = new WeekViewEvent(eventID, activity,
                    startingObj.getCalendar(), endingObj.getCalendar());
            eventID++;
            Toast.makeText(MyCalendar.this, "Successfully Added An Event", Toast.LENGTH_SHORT).show();
            mainEvents.add(newEvent);
            mWeekView.notifyDatasetChanged();
        } else if (requestCode == removeEventCode && resultCode == Activity.RESULT_OK) {
            long tempID = data.getLongExtra("eventID", -1);
            ListIterator<WeekViewEvent> listIterator = mainEvents.listIterator();
            while(listIterator.hasNext()){
                if (listIterator.next().getId()==tempID) {
                    listIterator.remove();
                    Log.wtf("REMOVED","WOOOO");
                    break;
                }
            }
            mWeekView.notifyDatasetChanged();
        }
    }
}
