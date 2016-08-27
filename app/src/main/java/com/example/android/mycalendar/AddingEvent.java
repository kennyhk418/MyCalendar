package com.example.android.mycalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    Date currentTime, sTime, eTime;
    Button okButton, clearButton, cancelButton;
    TextView currentDate, startingTime, endingTime;
    EditText activity;
    int sYear, sMonth, sDay;
    int sHour, sMin;
    int eHour, eMin;
    boolean isDatePassed = false;
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
        okButton = (Button) findViewById(R.id.submit);
        clearButton = (Button) findViewById(R.id.clear);
        cancelButton = (Button) findViewById(R.id.cancel);
        activity = (EditText) findViewById(R.id.activity);
        initializeVariables(); // initialize all the variables when it first creates

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
                        sHour = i;
                        sMin = i1;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
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
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePicker.show();
            }
        });

        //Click OK button to submit the event back to the calendar
        //It will check if the starting time is equal or after ending time. if so, make a flag
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sTime.after(eTime) || sTime.equals(eTime)) {
                    Toast.makeText(AddingEvent.this, "Starting Time cannot be later than Ending Time"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    isDatePassed = true;
                    String activityName = activity.getText().toString();
                    setResult(Activity.RESULT_OK, new Intent().putExtra("activity",activityName).putExtra("sYear", sYear)
                            .putExtra("sMonth", sMonth).putExtra("sDay", sDay).putExtra("sHour", sHour)
                            .putExtra("sMin", sMin).putExtra("eHour", eHour).putExtra("eMin", eMin)
                            .putExtra("isDatePassed", isDatePassed));
                    finish();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeVariables();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Method to initialize all the textView and variables to the current date and time
    public void initializeVariables(){
        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();
        sTime = currentTime;
        eTime = currentTime;
        sYear = calendar.get(Calendar.YEAR);
        sMonth = calendar.get(Calendar.MONTH);
        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        sHour = calendar.get(Calendar.HOUR_OF_DAY);
        sMin = calendar.get(Calendar.MINUTE);
        eHour = sHour;
        eMin = sMin;
        //Initialize TextView values
        currentDate.setText(dateFormat.format(currentTime));
        startingTime.setText(timeFormat.format(currentTime));
        endingTime.setText(timeFormat.format(currentTime));
    }
}