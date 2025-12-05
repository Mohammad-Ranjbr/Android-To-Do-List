package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
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
