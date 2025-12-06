package com.example.todo;

public interface TaskItemEventListener {
    void onDeleteButtonClick(Task task);
    void onEditButtonClick(Task task);
    void onItemCheckedChange(Task task);
}
