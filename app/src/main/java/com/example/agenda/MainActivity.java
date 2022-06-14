package com.example.agenda;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.agenda.Adapter.AdapterTask;
import com.example.agenda.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // main activity widgets
    TextView todayTextView;
    FloatingActionButton openNewTaskActivity;
    RecyclerView taskRecyclerView;
    // Adapter variable
    AdapterTask adapterTask;
    // Database variable
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database instance
        db = new Database(this);
        // attribute ids to the widgets
        todayTextView = findViewById(R.id.todayTextView);
        openNewTaskActivity = findViewById(R.id.addTask);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);

        // recycler view configurations
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setHasFixedSize(true);

        // load today's date to todayTextView
        changeDate(todayTextView);
        // load list with tasks
        loadTasks();
        // set onclick function
        openNewTaskActivity.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AddTask.class);
                        // start activity
                        reloadList.launch(intent);
                    }
                });
    }

    protected void changeDate(@NonNull TextView todayTextView) {
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

    public void loadTasks() {
        // instantiate adapterTask with new values from the database
        adapterTask = new AdapterTask(MainActivity.this, db.getTasks());
        // set new adapter for taskRecyclerView
        taskRecyclerView.setAdapter(adapterTask);
    }

    protected void printTasks() {
        Task[] tasks = db.getTasks();
        for (Task task : tasks) {
            System.out.println("[" + task.getId() + "] (" + task.getDone() + "): " + task.getDesc());
        }
    }

    ActivityResultLauncher<Intent> reloadList = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // reload list with tasks
                        loadTasks();
                        // print tasks to console
                        // printTasks();
                    }
                }
            });
}
