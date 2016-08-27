package com.example.android.mycalendar;

import java.util.Calendar;

/**
 * Created by lamki on 8/26/2016.
 */
public class TimeObject {
    int year, month, day;
    int hour, min;
    Calendar calendar = Calendar.getInstance();

    public TimeObject(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public TimeObject(int year, int month, int day, int hour, int min){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
    }

    public Calendar makeCalendar(){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        return calendar;
    }
}
