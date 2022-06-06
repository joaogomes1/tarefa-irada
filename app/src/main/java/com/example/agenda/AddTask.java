package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.agenda.Database;

public class AddTask extends AppCompatActivity {
    EditText taskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //taskEditText.findViewById(R.id.taskEditText);
    }

    public void addNewTask(View view) {
        String descricao = taskEditText.getText().toString().trim();
        System.out.println("ola");
    }

    public void cancelNewTask(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}