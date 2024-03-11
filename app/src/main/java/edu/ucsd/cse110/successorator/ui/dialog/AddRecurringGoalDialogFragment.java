package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.Date;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogAddRecurringGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

public class AddRecurringGoalDialogFragment extends DialogFragment {
    private FragmentDialogAddRecurringGoalBinding view;
    private DateHandler currentDate;
    private LocalDate startDate;

    public static AddRecurringGoalDialogFragment newInstance() {
        var fragment = new AddRecurringGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogAddRecurringGoalBinding.inflate(getLayoutInflater());
        startDate = currentDate.dateTime().toLocalDate();
        AddRecurringGoalDialogFragment thisFragment = this;

        Button changeStartDateButton = view.setDateButton;

        changeStartDateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                var dialogFragment = new DatePickerFragment();
                dialogFragment.setStartDate(startDate);
                dialogFragment.setRecurringGoalDialogFragment(thisFragment);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
            }
        });

        changeOptionText();

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Recurring Goal")
                .setView(view.getRoot())
                .setPositiveButton("Add", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void changeOptionText() {
        DateHandler newStartDateHandler = new DateHandler();
        newStartDateHandler.updateTodayDate(startDate.atStartOfDay());

        RadioButton weeklyButton = view.getRoot().findViewById(R.id.weekly_button);
        weeklyButton.setText("Weekly on " + newStartDateHandler.getWeekday());

        RadioButton monthlyButton = view.getRoot().findViewById(R.id.monthly_button);
        monthlyButton.setText("Monthly on " + newStartDateHandler.getWeekdayInMonth());

        RadioButton yearlyButton = view.getRoot().findViewById(R.id.yearly_button);
        yearlyButton.setText("Yearly on " + newStartDateHandler.getMonthAndDate());

        Button changeStartDateButton = view.setDateButton;
        changeStartDateButton.setText("Starting on " + newStartDateHandler.getMonthAndDate());
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var content = view.goalNameEditText.getText().toString().trim();

        if(content.equals("")) {
            dialog.dismiss();
            return;
        }

        MainActivity activity = (MainActivity) requireActivity();

        if(view.dailyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.DAILY, startDate);
            activity.addItemToRecurringList(rgoal);
        } else if(view.weeklyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.WEEKLY, startDate);
            activity.addItemToRecurringList(rgoal);
        } else if(view.monthlyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.MONTHLY, startDate);
            activity.addItemToRecurringList(rgoal);
        } else if(view.yearlyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.YEARLY, startDate);
            activity.addItemToRecurringList(rgoal);
        } else {
            throw new IllegalStateException("No radio button is checked.");
        }



        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    public void setCurrentDate(DateHandler currentDate) {
        this.currentDate = currentDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        changeOptionText();
    }
}
