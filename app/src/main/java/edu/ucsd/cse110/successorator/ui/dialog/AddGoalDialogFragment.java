package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import edu.ucsd.cse110.successorator.databinding.FragmentDialogAddGoalBinding;


public class AddGoalDialogFragment extends DialogFragment {
    private FragmentDialogAddGoalBinding view;

    public static AddGoalDialogFragment newInstance() {
        var fragment = new AddGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogAddGoalBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setView(view.getRoot())
                .setPositiveButton("Add", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var content = view.goalNameEditText.getText().toString().trim();

        if(content.equals("")) {
            dialog.dismiss();
            return;
        }

        var goal = new Goal(null, content, false);

        MainActivity activity = (MainActivity) requireActivity();
        activity.addItemToTodoList(goal);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
