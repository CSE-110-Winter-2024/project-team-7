package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogMovePendingGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.ui.pending.PendingFragment;

public class MovePendingGoalDialogFragment extends DialogFragment {
    private FragmentDialogMovePendingGoalBinding view;

    private static int goalPosition;

    public static MovePendingGoalDialogFragment newInstance(int position) {
        var fragment = new MovePendingGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        goalPosition = position;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogMovePendingGoalBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("Move Pending Goal")
                .setView(view.getRoot())
                .setPositiveButton("Move", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int position) {

        MainActivity activity = (MainActivity) requireActivity();
        Goal selected = PendingFragment.getAdapter().getItem(goalPosition);
        if(view.moveTodayButton.isChecked()) {
            activity.addItemToTodoList(selected);
            activity.deletePendingGoal(selected);
        } else if(view.moveTomorrowButton.isChecked()) {
            activity.addItemToTomorrowList(selected);
            activity.deletePendingGoal(selected);
        } else if(view.finishPendingButton.isChecked()) {
            activity.addItemToTodoList(selected);
            activity.moveToFinished(selected);
            activity.deletePendingGoal(selected);
        } else if(view.deletePendingButton.isChecked()) {
            activity.deletePendingGoal(selected);
        } else {
            throw new IllegalStateException("No radio button is checked.");
        }

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
