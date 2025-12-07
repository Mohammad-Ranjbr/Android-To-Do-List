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

public class ConfirmDialog extends DialogFragment {

    private Task task;
    private boolean isDeleteAll;
    private ConfirmCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.callback = (ConfirmCallback) context;
        this.task = getArguments().getParcelable(TaskDialog.TASK_DIALOG_KEY);
        this.isDeleteAll = (task == null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.confirm_dialog, null, false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView confirmDialogTextView = view.findViewById(R.id.tv_confirm_dialog_message);
        MaterialButton confirmDialogButton = view.findViewById(R.id.btn_confirm_confirmDialog);
        MaterialButton cancelDialogButton = view.findViewById(R.id.btn_cancel_confirmDialog);

        if (!isDeleteAll) {
            confirmDialogTextView.setText(getString(R.string.delete_item_message));
            confirmDialogButton.setOnClickListener(v -> {
                callback.onConfirmDelete(task);
                dismiss();
            });
            cancelDialogButton.setOnClickListener(v -> dismiss());
        } else {
            confirmDialogTextView.setText(getString(R.string.delete_all_message));
            confirmDialogButton.setOnClickListener(v -> {
                callback.onConfirmDeleteAll();
                dismiss();
            });
            cancelDialogButton.setOnClickListener(v -> dismiss());
        }

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
