package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Modelo banco de dados
 *
 *
 */

public class MainActivity extends AppCompatActivity {
    // days offset from today
    int currentOffset;
    // main activity widgets
    TextView dayTextView;
    Button previousDay;
    Button nextDay;
    FloatingActionButton openNewTaskActivity;
    FloatingActionButton goToToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set currentOffset to 0 (today)
        currentOffset = 0;
        // attribute ids to the widgets
        previousDay = findViewById(R.id.previousDay);
        dayTextView = findViewById(R.id.dayTextView);
        nextDay = findViewById(R.id.nextDay);
        openNewTaskActivity = findViewById(R.id.addTask);
        goToToday = findViewById(R.id.goToToday);

        // set previousDay text to "<"
        previousDay.setText("<");
        // load today's date to dayTextView
        changeDate(dayTextView);
        // set nextDay text to ">"
        nextDay.setText(">");
        // set onclick function
        openNewTaskActivity.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AddTask.class);
                        startActivity(intent);
                    }
                }
            );
        // go to today
        goToToday.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        currentOffset = 0;
                        changeDate(dayTextView);
                    }
                }
        );
    }

    public void goToNextDay(View view) {
        // increment currentOffset by one
        currentOffset++;
        changeDate(dayTextView);
    }

    public void goToPreviousDay(View view) {
        // decrement currentOffset by one
        currentOffset--;
        changeDate(dayTextView);
    }

    protected void changeDate(TextView dayTextView) {
        // Date object
        Date date = new Date();
        // when loading a day that is not today
        if (currentOffset != 0) {
            // Calendar object
            Calendar calendar = Calendar.getInstance();
            // set calendar to today
            calendar.setTime(date);
            // change calendar's day according to currentOffset
            calendar.add(Calendar.DATE, currentOffset);
            // set date with new date
            date = calendar.getTime();
        }
        // dayTextView string patter
        String pattern = "EEEE, dd/MM";
        // SimpleDateFormat object with Locale set to portuguese, Brazil
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("pt", "BR"));
        // create a date string formatted with simpleDateFormat
        String date_string = simpleDateFormat.format(date);
        // set dayTextView text to new date
        dayTextView.setText(date_string);
        // change floating button to visible if today
        if (currentOffset == 0)
            goToToday.setVisibility(View.INVISIBLE);
        else
            goToToday.setVisibility(View.VISIBLE);
    }
}