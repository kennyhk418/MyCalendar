package com.example.android.mycalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddingEvent extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    Calendar endcalendar = Calendar.getInstance();
    Date sTime, eTime;
    TextView currentDate, startingTime, endingTime;
    EditText activity;
    int sYear, sMonth, sDay;
    int sHour, sMin;
    int eHour, eMin;
    int currentTime;
    long eventID = -1;
    DatePickerDialog datePicker;
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_event);

        currentDate = (TextView) findViewById(R.id.date);
        startingTime = (TextView) findViewById(R.id.startingTime);
        endingTime = (TextView) findViewById(R.id.endingTime);
        activity = (EditText) findViewById(R.id.activity);
        initializeVariables(); // initialize all the variables when it first creates

        //Add up button to the page
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        //Click on the "Current Date" TextView to choose the desired date of an event
        //Default values = current date
        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePickerDialog(AddingEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        TimeObject tempDate = new TimeObject(i,i1,i2);
                        tempDate.setCalendar();
                        currentDate.setText(dateFormat.format(tempDate.getCalendar().getTime()));
                        sYear = i;
                        sMonth = i1;
                        sDay = i2;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        //Click on the "Starting Time" TextView to choose the Starting Time of an event
        //Default values = current time
        startingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(AddingEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        TimeObject tempTime = new TimeObject(i,i1);
                        tempTime.setCalendar();
                        startingTime.setText(timeFormat.format(tempTime.getCalendar().getTime()));
                        sTime = tempTime.getCalendar().getTime();

                        // Add one hour to the ending time when the starting time is changed
                        endcalendar.set(Calendar.HOUR_OF_DAY,i+1);
                        endcalendar.set(Calendar.MINUTE,i1);
                        eTime = endcalendar.getTime();
                        eHour = i+1;
                        eMin = i1;

                        endingTime.setText(timeFormat.format(endcalendar.getTime()));
                        sHour = i;
                        sMin = i1;
                    }
                }, currentTime, 0, false);
                timePicker.show();
            }
        });

        //Click on the "Ending Time" TextView to choose the Ending Time of an event
        //Default values = current time
        endingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(AddingEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        TimeObject tempTime = new TimeObject(i,i1);
                        tempTime.setCalendar();
                        endingTime.setText(timeFormat.format(tempTime.getCalendar().getTime()));
                        eTime = tempTime.getCalendar().getTime();
                        eHour = i;
                        eMin = i1;
                    }
                }, currentTime+1, 0, false);
                timePicker.show();
            }
        });
    }

    // Method to check if the time is valid, then save it
    private void saveEvent(){
        if (sTime.after(eTime) || sTime.equals(eTime)) {
            Toast.makeText(AddingEvent.this, "Starting Time cannot be later than Ending Time"
                    , Toast.LENGTH_SHORT).show();
        } else {
            String activityName = activity.getText().toString();
            setResult(Activity.RESULT_OK, new Intent().putExtra("activity",activityName).putExtra("sYear", sYear)
                    .putExtra("sMonth", sMonth).putExtra("sDay", sDay).putExtra("sHour", sHour)
                    .putExtra("sMin", sMin).putExtra("eHour", eHour).putExtra("eMin", eMin)
                    .putExtra("eventID", eventID));
            finish();
        }
    }

    //Method to initialize all the textView and variables to the current date and time
    public void initializeVariables(){
        Intent intent = getIntent();
        eventID = intent.getLongExtra("eventID",-1);
        currentTime = intent.getIntExtra("currentTime",-1);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,currentTime);
        endcalendar.set(Calendar.HOUR_OF_DAY,currentTime);
        endcalendar.set(Calendar.MINUTE,0);
        endcalendar.add(Calendar.HOUR_OF_DAY,1);

        sTime = calendar.getTime();
        eTime = endcalendar.getTime();
        sYear = calendar.get(Calendar.YEAR);
        sMonth = calendar.get(Calendar.MONTH);
        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        sHour = calendar.get(Calendar.HOUR_OF_DAY);
        sMin = 0;

        eHour = endcalendar.get(Calendar.HOUR_OF_DAY);
        eMin = sMin;
        //Initialize TextView values
        currentDate.setText(dateFormat.format(sTime));
        startingTime.setText(timeFormat.format(sTime));
        endingTime.setText(timeFormat.format(eTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.saveevent_addingevent, menu);
        return true;
    }

    //Overriding the up button to just go back up one stack
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                saveEvent();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}