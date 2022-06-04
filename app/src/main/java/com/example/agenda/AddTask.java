package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }

    public void addNewTask(View view) {

    }

    public void cancelNewTask(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}