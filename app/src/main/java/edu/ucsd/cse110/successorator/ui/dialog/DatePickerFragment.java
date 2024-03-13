package edu.ucsd.cse110.successorator.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.Calendar;

import edu.ucsd.cse110.successorator.MainActivity;

/* Taken from the Picker documentation at
 * https://developer.android.com/develop/ui/views/components/pickers
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private LocalDate startDate;
    private AddRecurringGoalDialogFragment recurringGoalDialogFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = startDate.getYear();
        int month = startDate.getMonthValue() - 1;
        int day = startDate.getDayOfMonth();

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        startDate = LocalDate.of(year, month+1, day);
        recurringGoalDialogFragment.setStartDate(startDate);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setRecurringGoalDialogFragment(AddRecurringGoalDialogFragment recurringGoalDialogFragment) {
        this.recurringGoalDialogFragment = recurringGoalDialogFragment;
    }
}