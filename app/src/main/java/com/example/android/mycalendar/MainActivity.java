package com.example.android.mycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button tempButton = null;

    TextView startingTime, endingTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startingTime = (TextView) findViewById(R.id.startingTime);
        endingTime = (TextView) findViewById(R.id.endingTime);
        tempButton = (Button) findViewById(R.id.button);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent toMain = new Intent(MainActivity.this, MyCalendar.class);
                Intent toMain = new Intent(MainActivity.this, MyCalendar.class);
                startActivity(toMain);
            }
        });
    }


}
