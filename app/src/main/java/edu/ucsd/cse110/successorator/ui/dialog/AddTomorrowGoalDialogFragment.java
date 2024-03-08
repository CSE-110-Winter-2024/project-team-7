package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ucsd.cse110.successorator.ui.tomorrow.TomorrowFragment;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogAddTomorrowGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;


public class AddTomorrowGoalDialogFragment extends DialogFragment {
    private FragmentDialogAddTomorrowGoalBinding view;
    private DateHandler currentDate;

    public static AddTomorrowGoalDialogFragment newInstance() {
        var fragment = new AddTomorrowGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogAddTomorrowGoalBinding.inflate(getLayoutInflater());

        RadioButton weeklyButton = view.getRoot().findViewById(R.id.weekly_button);
        weeklyButton.setText("Weekly on " + currentDate.getWeekday());

        RadioButton monthlyButton = view.getRoot().findViewById(R.id.monthly_button);
        monthlyButton.setText("Monthly on " + currentDate.getWeekdayInMonth());

        RadioButton yearlyButton = view.getRoot().findViewById(R.id.yearly_button);
        yearlyButton.setText("Yearly on " + currentDate.getMonthAndDate());

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

        TomorrowFragment activity = (TomorrowFragment) requireActivity();

//        if(view.oneTimeButton.isChecked()) {
//            var goal = new Goal(null, content, false);
//            activity.addItemToTodoList(goal);
//        } else if(view.dailyButton.isChecked()) {
//            var rgoal = new RecurringGoal(null, content, RecurringGoal.DAILY, currentDate.dateTime().toLocalDate());
//            activity.addItemToRecurringList(rgoal);
//        } else if(view.weeklyButton.isChecked()) {
//            var rgoal = new RecurringGoal(null, content, RecurringGoal.WEEKLY, currentDate.dateTime().toLocalDate());
//            activity.addItemToRecurringList(rgoal);
//        } else if(view.monthlyButton.isChecked()) {
//            var rgoal = new RecurringGoal(null, content, RecurringGoal.MONTHLY, currentDate.dateTime().toLocalDate());
//            activity.addItemToRecurringList(rgoal);
//        } else if(view.yearlyButton.isChecked()) {
//            var rgoal = new RecurringGoal(null, content, RecurringGoal.YEARLY, currentDate.dateTime().toLocalDate());
//            activity.addItemToRecurringList(rgoal);
//        } else {
//            throw new IllegalStateException("No radio button is checked.");
//        }



        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    public void setCurrentDate(DateHandler currentDate) {
        this.currentDate = currentDate;
    }
}
