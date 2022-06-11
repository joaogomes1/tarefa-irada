package com.example.agenda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTask extends AppCompatActivity {
    // add task widgets
    EditText task;
    Button confirm;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // attribute ids to the widgets
        task = (EditText)findViewById(R.id.taskEditText);
        confirm = (Button)findViewById(R.id.confirmTaskButton);
        cancel = (Button)findViewById(R.id.cancelTaskButton);

        // set onclick function for confirm
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = task.getText().toString().trim();
                if (description.isEmpty()) {
                    Toast.makeText(
                            AddTask.this, "Adicione uma descrição", Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                String query = "INSERT INTO agenda (description) VALUES (" + description + ");";
                // insert value into database
                Database db = new Database();
                // db.executeQuery(query);
                // close activity
                finish();
            }
        });

        // set onclick function for cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}