package com.example.todo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity implements TaskCallback, TaskItemEventListener {

    private TaskAdapter taskAdapter;
    private SQLiteHelper sqLiteHelper;
    private LinearLayout emptyStateContainer;
    private TextView emptyResultTextView;
    private boolean isSearching = false;
    private LottieAnimationView lottieAnimationView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        this.sqLiteHelper = new SQLiteHelper(this);
        this.taskAdapter = new TaskAdapter(this);
        this.emptyStateContainer = findViewById(R.id.empty_state_container);
        this.emptyResultTextView = findViewById(R.id.tv_empty_message);
        this.lottieAnimationView = findViewById(R.id.lottie_main);

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

        EditText searchEditText = findViewById(R.id.et_main_search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSearching = s.length() > 0;
                if (isSearching) {
                    taskAdapter.addItems(sqLiteHelper.searchInTasks(s.toString()));
                } else {
                    taskAdapter.addItems(sqLiteHelper.getTasks());
                }
            }
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
        } else {
            Log.e(TAG, "editTask: " + "task did not updated");
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

    @Override
    public void onItemCheckedChange(Task task) {
        sqLiteHelper.updateTask(task);
        taskAdapter.addItems(sqLiteHelper.getTasks());
    }

    @Override
    public void onItemCountChanged(int size) {
        if(size == 0) {
            emptyStateContainer.setVisibility(View.VISIBLE);
            if (isSearching) {
                emptyResultTextView.setText(getString(R.string.empty_search_message));
                lottieAnimationView.setAnimation(R.raw.non_data_found);
            } else {
                emptyResultTextView.setText(getString(R.string.empty_tasks_message));
                lottieAnimationView.setAnimation(R.raw.no_history);
            }
        } else {
            emptyStateContainer.setVisibility(View.GONE);
        }
    }

}