package com.example.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements TaskCallback, TaskItemEventListener {

    private TaskAdapter taskAdapter;
    private SQLiteHelper sqLiteHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        this.sqLiteHelper = new SQLiteHelper(this);
        taskAdapter = new TaskAdapter(this);

        taskAdapter.addItems(sqLiteHelper.getTasks());

        RecyclerView recyclerView = findViewById(R.id.rv_main_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);

        View addNewTaskButton = findViewById(R.id.fab_main_addNewTask);
        addNewTaskButton.setOnClickListener(v -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.show(getSupportFragmentManager(), null);
        });

        View deleteAllTasksButton = findViewById(R.id.iv_main_clearAllTasks);
        deleteAllTasksButton.setOnClickListener(v -> {
            sqLiteHelper.deleteAllTasks();
            taskAdapter.deleteAllTasks();
        });

    }

    @Override
    public void addNewTask(Task task) {
        long newTaskId = sqLiteHelper.addTask(task);
        if (newTaskId != -1) {
            task.setId(newTaskId);
            taskAdapter.addTask(task);
        } else {
            Log.e(TAG, "addNewTask: " + "task did not inserted");
        }
    }

    @Override
    public void editTask(Task task) {
        int result = sqLiteHelper.updateTask(task);
        if (result > 0) {
            taskAdapter.updateTask(task);
        }
    }

    @Override
    public void onDeleteButtonClick(Task task) {
        int result = sqLiteHelper.deleteTask(task);
        if(result > 0) {
            taskAdapter.deleteTask(task);
        } else {
            Log.e(TAG, "onDeleteButtonClick: " + "task did not inserted");
        }
    }

    @Override
    public void onEditButtonClick(Task task) {
        EditTaskDialog editTaskDialog = EditTaskDialog.newInstance(task);
        editTaskDialog.show(getSupportFragmentManager(), null);
    }

}