package com.example.todo;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        RecyclerView recyclerView = findViewById(R.id.rv_main_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        TaskAdapter taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);


        View addNewTaskButton = findViewById(R.id.fab_main_addNewTask);
        addNewTaskButton.setOnClickListener(v -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.show(getSupportFragmentManager(), null);
        });

    }
}