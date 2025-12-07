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

import com.google.android.material.button.MaterialButton;

public class ConfirmDialog extends DialogFragment {

    private Task task;
    private ConfirmCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.callback = (ConfirmCallback) context;
        this.task = getArguments().getParcelable(TaskDialog.TASK_DIALOG_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.confirm_dialog, null, false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        MaterialButton confirmDialogButton = view.findViewById(R.id.btn_confirm_confirmDialog);
        MaterialButton cancelDialogButton = view.findViewById(R.id.btn_cancel_confirmDialog);

        confirmDialogButton.setOnClickListener(v -> {
            callback.onConfirmDelete(task);
            dismiss();
        });
        cancelDialogButton.setOnClickListener(v -> dismiss());

        return dialog;
    }

    public static ConfirmDialog newInstance(Task task) {
        Bundle args = new Bundle();
        args.putParcelable(TaskDialog.TASK_DIALOG_KEY, task);
        ConfirmDialog fragment = new ConfirmDialog();
        fragment.setArguments(args);
        return fragment;
    }

}
