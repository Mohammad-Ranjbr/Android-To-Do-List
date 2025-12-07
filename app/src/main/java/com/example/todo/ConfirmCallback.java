package com.example.todo;

public interface ConfirmCallback {
    void onConfirmDelete(Task task);
    void onConfirmDeleteAll();
}
