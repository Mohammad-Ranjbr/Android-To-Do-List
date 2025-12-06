package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    public void addItems(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }
    public void deleteAllTasks() {
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final ImageView deleteTaskButton;
        private final ImageView editTaskButton;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.task_item_checkBox);
            deleteTaskButton = itemView.findViewById(R.id.task_item_delete_button);
            editTaskButton = itemView.findViewById(R.id.task_item_edit_button);
        }

        public void bindTask(Task task) {
            checkBox.setText(task.getTitle());
            checkBox.setChecked(task.isCompleted());
        }

    }

}
