package com.example.todo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditTaskDialog extends DialogFragment {

    private Task task;
    private TaskCallback callback;
    public static final String EDIT_TASK_DIALOG_KEY = "task";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (TaskCallback) context;
        task = getArguments().getParcelable(EDIT_TASK_DIALOG_KEY);
        if(task == null) {
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null, false);

        TextInputLayout editTaskTextInputLayout = view.findViewById(R.id.etl_editTask_dialog);
        TextInputEditText editTaskTextInputEditText = view.findViewById(R.id.et_editTask_dialog);
        editTaskTextInputEditText.setText(task.getTitle());
        View editTaskDialogButton = view.findViewById(R.id.btn_editTask_update);
        editTaskDialogButton.setOnClickListener(v -> {
            if (editTaskTextInputEditText.length() > 0) {
                task.setTitle(editTaskTextInputEditText.getText().toString());
                callback.editTask(task);
                dismiss();
            } else {
                editTaskTextInputLayout.setError(getString(R.string.error_empty_title));
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    public static EditTaskDialog newInstance(Task task) {
        Bundle args = new Bundle();
        args.putParcelable(EDIT_TASK_DIALOG_KEY, task);

        EditTaskDialog fragment = new EditTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

}
