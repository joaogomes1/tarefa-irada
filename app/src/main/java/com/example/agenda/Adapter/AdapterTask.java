package com.example.agenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

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
                        new Database(context).deleteTask(task.getId());
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
