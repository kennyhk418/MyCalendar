package com.example.android.mycalendar;

import java.util.Calendar;

/**
 * Created by lamki on 8/26/2016.
 */
public class TimeObject {
    int year, month, day;
    int hour, min;
    Calendar calendar = Calendar.getInstance();

    public TimeObject (){
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.min = calendar.get(Calendar.MINUTE);
    }

    public TimeObject(int hour, int min){
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = hour;
        this.min = min;
    }

    public TimeObject(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.min = calendar.get(Calendar.MINUTE);
    }

    public TimeObject(int year, int month, int day, int hour, int min){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
    }

    public void setCalendar(){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
    }

    public Calendar getCalendar(){
        return calendar;
    }
}
