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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddingEvent extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();
    Date currentTime, sTime, eTime;
    Button okButton, cancelButton;
    TextView currentDate, startingTime, endingTime, activity;
    int sYear, sMonth, sDay;
    int sHour, sMin;
    int eHour, eMin;
    boolean isDatePassed = false;
    DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_event);

        currentDate = (TextView) findViewById(R.id.date);
        startingTime = (TextView) findViewById(R.id.startingTime);
        endingTime = (TextView) findViewById(R.id.endingTime);
        okButton = (Button) findViewById(R.id.submit);

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

        final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEE");

        //Initialize TextView values
        currentDate.setText(dateFormat.format(currentTime));
        startingTime.setText(timeFormat.format(currentTime));
        endingTime.setText(timeFormat.format(currentTime));


        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePickerDialog(AddingEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar tempCal = calendar;
                        tempCal.set(Calendar.YEAR, i);
                        tempCal.set(Calendar.MONTH, i1);
                        tempCal.set(Calendar.DAY_OF_MONTH, i2);
                        currentDate.setText(dateFormat.format(tempCal.getTime()));
                        sYear = i;
                        sMonth = i1;
                        sDay = i2;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        startingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(AddingEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar tempCal = calendar;
                        tempCal.set(Calendar.HOUR_OF_DAY, i);
                        tempCal.set(Calendar.MINUTE, i1);
                        startingTime.setText(timeFormat.format(tempCal.getTime()));
                        sTime = tempCal.getTime();
                        sHour = i;
                        sMin = i1;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePicker.show();
            }
        });

        endingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(AddingEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar tempCal = calendar;
                        tempCal.set(Calendar.HOUR_OF_DAY, i);
                        tempCal.set(Calendar.MINUTE, i1);
                        endingTime.setText(timeFormat.format(tempCal.getTime()));
                        eTime = tempCal.getTime();
                        eHour = i;
                        eMin = i1;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePicker.show();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sTime.after(eTime) || sTime.equals(eTime)) {
                    Toast.makeText(AddingEvent.this, "Starting Time cannot be later than Ending Time"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    isDatePassed = true;
                    setResult(Activity.RESULT_OK, new Intent().putExtra("sYear", sYear)
                            .putExtra("sMonth", sMonth).putExtra("sDay", sDay).putExtra("sHour", sHour)
                            .putExtra("sMin", sMin).putExtra("eHour", eHour).putExtra("eMin", eMin)
                            .putExtra("isDatePassed", isDatePassed));
                    finish();
                }
            }
        });
    }
}