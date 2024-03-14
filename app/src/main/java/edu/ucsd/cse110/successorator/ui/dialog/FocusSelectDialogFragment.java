package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.databinding.FocusModeSelectionBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class FocusSelectDialogFragment extends DialogFragment {
    private FocusModeSelectionBinding view;
    public static FocusSelectDialogFragment newInstance() {
        var fragment = new FocusSelectDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FocusModeSelectionBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("Select A Focus")
                .setView(view.getRoot())
                .setPositiveButton("Done", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {

        MainActivity activity = (MainActivity) requireActivity();
        String context = "All";
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
        activity.setFocus(context);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        MainActivity activity = (MainActivity) requireActivity();
        activity.setFocus("All");
        dialog.cancel();
    }
}
