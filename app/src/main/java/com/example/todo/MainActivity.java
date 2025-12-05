package com.example.todo;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        View addNewTaskButton = findViewById(R.id.fab_main_addNewTask);
        addNewTaskButton.setOnClickListener(v -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.show(getSupportFragmentManager(), null);
        });

    }
}