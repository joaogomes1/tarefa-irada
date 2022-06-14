package com.example.agenda.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.Database;
import com.example.agenda.R;
import com.example.agenda.model.Task;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.TaskViewHolder> {
    private final Context context;
    Task[] tasks;

    public AdapterTask(Context context, Task[] tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskItem = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(taskItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // get a single task class in the array
        Task task = tasks[position];
        // set values to the widgets
        holder.done.setChecked(task.getDone() == 1);
        holder.description.setText(task.getDesc());
        // on click
        holder.done.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        boolean checked = holder.done.isChecked();
                        int done = (checked ? 1 : 0);
                        // update sql
                        new Database(context).updateDone(task.getId(), done);
                        // update done in task class
                        task.setDone(done);
                    }
                }
        );
        holder.delete.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        // instantiate builder
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        // set message for dialog
                        dialog.setMessage(R.string.delete_dialog_message);
                        // set icon
                        dialog.setIcon(android.R.drawable.ic_dialog_alert);
                        // set "yes" button
                        dialog.setPositiveButton(R.string.positive_button_dialog, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // delete item from database
                                new Database(context).deleteTask(task.getId());
                                // make toast
                                String toastResource = context.getResources().getString(R.string.deleted_task_toast);
                                String toastMessage = String.format(toastResource, task.getDesc());
                                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                        // set "no" button
                        dialog.setNegativeButton(R.string.negative_button_dialog, null);
                        dialog.show();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        // get length of items in tasks array
        return this.tasks.length;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        // widgets
        CheckBox done;
        TextView description;
        ImageButton delete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            // attribute ids to the widgets
            done = itemView.findViewById(R.id.checkBox);
            description = itemView.findViewById(R.id.descriptionTextView);
            delete = itemView.findViewById(R.id.deleteTaskButton);
        }
    }
}
