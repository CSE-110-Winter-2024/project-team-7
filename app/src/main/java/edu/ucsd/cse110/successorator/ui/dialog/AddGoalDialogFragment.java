package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import edu.ucsd.cse110.successorator.databinding.FragmentDialogAddGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;


public class AddGoalDialogFragment extends DialogFragment {
    private FragmentDialogAddGoalBinding view;
    private DateHandler currentDate;

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

        RadioButton weeklyButton = view.getRoot().findViewById(R.id.weekly_button);
        weeklyButton.setText("Weekly on " + currentDate.getWeekday(0));

        RadioButton monthlyButton = view.getRoot().findViewById(R.id.monthly_button);
        monthlyButton.setText("Monthly on " + currentDate.getWeekdayInMonth(0));

        RadioButton yearlyButton = view.getRoot().findViewById(R.id.yearly_button);
        yearlyButton.setText("Yearly on " + currentDate.getMonthAndDate(0));

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

        if(view.oneTimeButton.isChecked()) {
            var goal = new Goal(null, content, false);
            activity.addItemToTodoList(goal);
        } else if(view.dailyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.DAILY, currentDate.dateTime().toLocalDate(), context);
            activity.addItemToRecurringList(rgoal);
        } else if(view.weeklyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.WEEKLY, currentDate.dateTime().toLocalDate(), context);
            activity.addItemToRecurringList(rgoal);
        } else if(view.monthlyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.MONTHLY, currentDate.dateTime().toLocalDate(), context);
            activity.addItemToRecurringList(rgoal);
        } else if(view.yearlyButton.isChecked()) {
            var rgoal = new RecurringGoal(null, content, RecurringGoal.YEARLY, currentDate.dateTime().toLocalDate(), context);
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
}
