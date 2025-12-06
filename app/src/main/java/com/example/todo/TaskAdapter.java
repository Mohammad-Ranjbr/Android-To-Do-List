package com.example.todo;

import android.annotation.SuppressLint;
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
    private final TaskItemEventListener taskItemEventListener;

    public TaskAdapter(TaskItemEventListener taskItemEventListener) {
        this.taskItemEventListener = taskItemEventListener;
    }

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

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteAllTasks() {
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public void deleteTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                this.tasks.remove(task);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                notifyItemChanged(i);
                break;
            }
        }
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
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setText(task.getTitle());
            checkBox.setChecked(task.isCompleted());

            deleteTaskButton.setOnClickListener(v -> taskItemEventListener.onDeleteButtonClick(task));
            editTaskButton.setOnClickListener(v -> taskItemEventListener.onEditButtonClick(task));

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setCompleted(isChecked);
                taskItemEventListener.onItemCheckedChange(task);
            });

        }

    }

}
