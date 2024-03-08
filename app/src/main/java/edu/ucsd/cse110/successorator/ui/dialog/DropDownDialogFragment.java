package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.ui.pending.PendingFragment;
import edu.ucsd.cse110.successorator.ui.recurring.RecurringFragment;
import edu.ucsd.cse110.successorator.ui.tomorrow.TomorrowFragment;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogDropDownBinding;

public class DropDownDialogFragment extends DialogFragment {
    private FragmentDialogDropDownBinding view;

    public static DropDownDialogFragment newInstance() {
        var fragment = new DropDownDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogDropDownBinding.inflate(getLayoutInflater());

        TextView titleTextView = new TextView(requireContext());
        titleTextView.setText("Select View");
        titleTextView.setGravity(Gravity.CENTER);

        Button todayButton = view.todayButton;
        Button tomorrowButton = view.tomorrowButton;
        Button recurringButton = view.recurringButton;
        Button pendingButton = view.pendingButton;

        // Set onClickListeners for the buttons
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Today button
                // Start TodayActivity
                startActivity(new Intent(requireContext(), MainActivity.class));
                dismiss(); // Dismiss the dialog after navigation
            }
        });

        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Tomorrow button
                // Start TomorrowActivity
                startActivity(new Intent(requireContext(), TomorrowFragment.class));
                dismiss(); // Dismiss the dialog after navigation
            }
        });

        recurringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Recurring button
                // Start RecurringActivity
                startActivity(new Intent(requireContext(), RecurringFragment.class));
                dismiss(); // Dismiss the dialog after navigation
            }
        });

        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Pending button
                // Start PendingActivity
                startActivity(new Intent(requireContext(), PendingFragment.class));
                dismiss(); // Dismiss the dialog after navigation
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setCustomTitle(titleTextView)
                .setView(view.getRoot())
                .create();
    }
}
