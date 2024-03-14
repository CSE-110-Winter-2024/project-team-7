package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogAddPendingGoalBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogAddRecurringGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class AddPendingGoalDialogFragment extends DialogFragment {
    private FragmentDialogAddPendingGoalBinding view;

    public static AddPendingGoalDialogFragment newInstance() {
        var fragment = new AddPendingGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogAddPendingGoalBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Pending Goal")
                .setView(view.getRoot())
                .setPositiveButton("Add", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var content = view.pendingGoalText.getText().toString().trim();

        if(content.equals("")) {
            dialog.dismiss();
            return;
        }

        MainActivity activity = (MainActivity) requireActivity();
        String context = "Home";
        if (view.buttonH.isChecked()) {
            context = "Home";
        } else if (view.buttonW.isChecked()) {
            context = "Work";
        } else if (view.buttonS.isChecked()) {
            context = "School";
        } else if (view.buttonE.isChecked()) {
            context = "Errands";
        } else {
            throw new IllegalStateException("No radio button is checked.");
        }

        activity.addPendingItemToTodoList(new Goal(null, content, false, false, context));
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
