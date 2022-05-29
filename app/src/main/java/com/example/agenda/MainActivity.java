package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // days offset from today
    int currentOffset;
    // main activity widgets
    TextView dayTextView;
    Button previousDay;
    Button nextDay;

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

        // set previousDay text to "<"
        previousDay.setText("<");
        // load today's date to dayTextView
        changeDate(dayTextView);
        // set nextDay text to ">"
        nextDay.setText(">");
    }

    public void goToNextDay(View view) {
        // increment currentOffset by one
        currentOffset++;
        // change date
        changeDate(dayTextView);
    }

    public void goToPreviousDay(View view) {
        // decrement currentOffset by one
        currentOffset--;
        changeDate(dayTextView);
    }

    // Dialog: https://developer.android.com/guide/topics/ui/dialogs?hl=pt-br#CustomLayout

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
    }
}