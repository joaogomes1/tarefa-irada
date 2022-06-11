package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // main activity widgets
    TextView todayTextView;
    FloatingActionButton openNewTaskActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // attribute ids to the widgets
        todayTextView = findViewById(R.id.todayTextView);
        openNewTaskActivity = findViewById(R.id.addTask);

        // load today's date to todayTextView
        changeDate(todayTextView);
        // set onclick function
        openNewTaskActivity.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AddTask.class);
                        startActivity(intent);
                    }
                }
            );
    }

    protected void changeDate(TextView todayTextView) {
        // Date object
        Date date = new Date();
        // todayTextView string patter
        String pattern = "EEEE, dd/MM";
        // SimpleDateFormat object with Locale set to portuguese, Brazil
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("pt", "BR"));
        // create a date string formatted with simpleDateFormat
        String date_string = simpleDateFormat.format(date);
        // set todayTextView text to new date
        todayTextView.setText(date_string);
    }
}