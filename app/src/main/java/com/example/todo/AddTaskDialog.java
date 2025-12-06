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

public class AddTaskDialog extends DialogFragment {

    private AddNewTaskCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.callback = (AddNewTaskCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null, false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputLayout addNewTaskTextInputLayout = view.findViewById(R.id.etl_addTask_dialog);
        TextInputEditText addNewTaskTextInputEditText = view.findViewById(R.id.et_addTask_dialog);
        View addNewTaskDialogButton = view.findViewById(R.id.btn_addTask_save);

        addNewTaskDialogButton.setOnClickListener(v -> {
            if (addNewTaskTextInputEditText.length() > 0) {
                Task task = new Task();
                task.setTitle(addNewTaskTextInputEditText.getText().toString());
                task.setCompleted(false);
                callback.addNewTask(task);
                dismiss();
            } else {
                addNewTaskTextInputLayout.setError(getString(R.string.error_empty_title));
            }
        });

        return dialog;
    }

}
