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
    // Database instance
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // attribute ids to the widgets
        task = findViewById(R.id.taskEditText);
        confirm = findViewById(R.id.confirmTaskButton);
        cancel = findViewById(R.id.cancelTaskButton);

        // set onclick function for confirm
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = task.getText().toString().trim();
                if (description.isEmpty()) {
                    Toast.makeText(
                            AddTask.this, R.string.add_description_toast, Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                // insert value into database
                db.addTask(description);
                // close activity with result ok
                setResult(RESULT_OK);
                finish();
            }
        });

        // set onclick function for cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close activity with result canceled
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // close activity with result canceled
        setResult(RESULT_CANCELED);
        finish();
    }
}
