package com.example.android.mycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowingEvent extends AppCompatActivity {
    Date currentTime, sTime, eTime;
    int year, month, day, shour, smin, ehour,emin;
    long eventID;
    String activity;
    Button back;
    TextView currentDate, startingTime, endingTime, eventName;
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_event);

        currentDate = (TextView)findViewById(R.id.date);
        startingTime = (TextView)findViewById(R.id.startingTime);
        endingTime = (TextView)findViewById(R.id.endingTime);
        eventName = (TextView)findViewById(R.id.activity);
        back = (Button)findViewById(R.id.back);

        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        month = intent.getIntExtra("month",-1);
        day = intent.getIntExtra("day",-1);
        shour = intent.getIntExtra("shour",-1);
        smin = intent.getIntExtra("smin",-1);
        ehour = intent.getIntExtra("ehour",-1);
        emin = intent.getIntExtra("emin",-1);
        eventID = intent.getIntExtra("eventID",-1);
        activity = intent.getStringExtra("activity");

        Calendar sTime = Calendar.getInstance();
        Calendar eTime = Calendar.getInstance();
        sTime.set(Calendar.YEAR,year);
        sTime.set(Calendar.MONTH,month);
        sTime.set(Calendar.DAY_OF_MONTH,day);
        sTime.set(Calendar.HOUR_OF_DAY,shour);
        sTime.set(Calendar.MINUTE,smin);
        eTime.set(Calendar.HOUR_OF_DAY,ehour);
        eTime.set(Calendar.MINUTE,emin);
        currentDate.setText(dateFormat.format(sTime.getTime()));
        startingTime.setText(timeFormat.format(sTime.getTime()));
        endingTime.setText(timeFormat.format(eTime.getTime()));
        eventName.setText(activity);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
