package com.example.todo;

public interface TaskCallback {
    void addNewTask(Task task);
    void editTask(Task task);
}
