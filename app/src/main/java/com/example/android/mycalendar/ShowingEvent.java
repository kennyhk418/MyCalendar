package com.example.android.mycalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class ShowingEvent extends AppCompatActivity {
    int year, month, day, shour, smin, ehour,emin;
    long eventID;
    String activity;
    TextView currentDate, startingTime, endingTime, eventName;
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_event);

        //Add up button to the page
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        currentDate = (TextView)findViewById(R.id.date);
        startingTime = (TextView)findViewById(R.id.startingTime);
        endingTime = (TextView)findViewById(R.id.endingTime);
        eventName = (TextView)findViewById(R.id.activity);

        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        month = intent.getIntExtra("month",-1);
        day = intent.getIntExtra("day",-1);
        shour = intent.getIntExtra("shour",-1);
        smin = intent.getIntExtra("smin",-1);
        ehour = intent.getIntExtra("ehour",-1);
        emin = intent.getIntExtra("emin",-1);
        eventID = intent.getLongExtra("eventID",-1);
        activity = intent.getStringExtra("activity");
        Log.wtf("EventID in show event",String.valueOf(eventID));
        TimeObject startingObj = new TimeObject(year,month,day,shour,smin);
        TimeObject endingObj = new TimeObject(year,month,day,ehour,emin);
        startingObj.setCalendar();
        endingObj.setCalendar();
        currentDate.setText(dateFormat.format(startingObj.getCalendar().getTime()));
        startingTime.setText(timeFormat.format(startingObj.getCalendar().getTime()));
        endingTime.setText(timeFormat.format(endingObj.getCalendar().getTime()));
        eventName.setText(activity);
    }

    private void deleteEvent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowingEvent.this);
        builder.setMessage("Are you sure that you want to delete this event?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                setResult(RESULT_OK,new Intent().putExtra("eventID",eventID));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delevent_showingevent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.deleteEvent:
                deleteEvent();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}
