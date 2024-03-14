package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogDeleteRecurringBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogMovePendingGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.ui.pending.PendingFragment;
import edu.ucsd.cse110.successorator.ui.recurring.RecurringFragment;

public class DeleteRecurringGoalDialogFragment  extends DialogFragment {
    private FragmentDialogDeleteRecurringBinding view;

    private static int goalPosition;

    public static DeleteRecurringGoalDialogFragment newInstance(int position) {
        var fragment = new DeleteRecurringGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogDeleteRecurringBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("Do You Want To Delete This Goal?")
                .setView(view.getRoot())
                .setPositiveButton("Yes", this::onPositiveButtonClick)
                .setNegativeButton("No", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int position) {

        MainActivity activity = (MainActivity) requireActivity();
        RecurringGoal selected = RecurringFragment.getAdapter().getItem(goalPosition);
        activity.deleteRecurringGoal(selected);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

}
