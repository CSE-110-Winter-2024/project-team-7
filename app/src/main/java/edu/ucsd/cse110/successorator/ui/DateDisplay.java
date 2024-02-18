package edu.ucsd.cse110.successorator.ui;

import edu.ucsd.cse110.successorator.lib.util.Observer;
import android.widget.TextView;

public class DateDisplay implements Observer<String> {
    private final TextView dateTextView;

    public DateDisplay(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }

    @Override
    public void onChanged(String formattedDate) {
        dateTextView.setText(formattedDate);
    }
}