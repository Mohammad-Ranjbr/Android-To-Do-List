package com.example.todo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TaskDialog extends DialogFragment {

    private Task task = null;
    private TaskCallback callback;
    private boolean isEditMode;
    public static final String TASK_DIALOG_KEY = "task";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.callback = (TaskCallback) context;
        this.task = getArguments().getParcelable(TASK_DIALOG_KEY);
        this.isEditMode = (task != null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task, null, false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView taskDialogTextView = view.findViewById(R.id.tv_title_taskDialog);
        TextInputLayout taskDialogTextInputLayout = view.findViewById(R.id.etl_task_dialog);
        TextInputEditText taskDialogTextInputEditText = view.findViewById(R.id.et_task_dialog);
        MaterialButton taskDialogButton = view.findViewById(R.id.btn_task_save);

        if (!isEditMode) {
            taskDialogTextView.setText(getString(R.string.add_task_dialog_title));
            taskDialogButton.setText(getString(R.string.add_task_dialog_save_button_text));
            taskDialogButton.setOnClickListener(v -> {
                if (taskDialogTextInputEditText.length() > 0) {
                    Task task = new Task();
                    task.setTitle(taskDialogTextInputEditText.getText().toString());
                    task.setCompleted(false);
                    callback.addNewTask(task);
                    dismiss();
                } else {
                    taskDialogTextInputLayout.setError(getString(R.string.error_empty_title));
                }
            });
        } else {
            taskDialogTextView.setText(getString(R.string.edit_task_dialog_title));
            taskDialogButton.setText(getString(R.string.edit_task_dialog_update_button_text));
            taskDialogTextInputEditText.setText(task.getTitle());
            taskDialogButton.setOnClickListener(v -> {
                if (taskDialogTextInputEditText.length() > 0) {
                    task.setTitle(taskDialogTextInputEditText.getText().toString());
                    callback.editTask(task);
                    dismiss();
                } else {
                    taskDialogTextInputLayout.setError(getString(R.string.error_empty_title));
                }
            });
        }

        return dialog;
    }

    public static TaskDialog newInstance(Task task) {
        Bundle args = new Bundle();
        args.putParcelable(TASK_DIALOG_KEY, task);
        TaskDialog fragment = new TaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

}
