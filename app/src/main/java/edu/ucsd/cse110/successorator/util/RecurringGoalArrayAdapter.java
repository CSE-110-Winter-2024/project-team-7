package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

public class RecurringGoalArrayAdapter extends ArrayAdapter<RecurringGoal> {
    private Context mContext;
    private int mResource;

    public RecurringGoalArrayAdapter(Context context, int resource, List<RecurringGoal> goals) {
        super(context, resource, goals);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_goal, parent, false);
        }

        RecurringGoal goal = getItem(position);
        if (goal != null) {
            TextView goalDescriptionTextView = convertView.findViewById(R.id.goalDescriptionTextView);
            TextView contextSymbolView = convertView.findViewById(R.id.contextSymbolView);

            String recurrence = "";
            switch (goal.getRecurringType()) {
                case RecurringGoal.DAILY:
                    recurrence = ": Daily";
                    break;
                case RecurringGoal.WEEKLY:
                    var weekday = goal.getStartDate().getDayOfWeek().toString();
                    recurrence = ": Weekly on " + weekday.substring(0,1) + weekday.substring(1).toLowerCase();
                    break;
                case RecurringGoal.MONTHLY:
                    var month = goal.getStartDate().getMonth().toString();
                    var date = goal.getStartDate().getDayOfMonth();
                    recurrence = ": Monthly on " + month.substring(0,1) + month.substring(1).toLowerCase() + " " + date;
                    break;
                case RecurringGoal.YEARLY:
                    var month1 = goal.getStartDate().getMonth().toString();
                    var date1 = goal.getStartDate().getDayOfMonth();
                    var year = goal.getStartDate().getYear();
                    recurrence = ": Yearly on " + month1.substring(0,1) + month1.substring(1).toLowerCase() + " " + date1 + ", " + year;
                    break;

            }
            goalDescriptionTextView.setText(goal.content() + recurrence);

            switch (goal.getContext()) {
                case "Home":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_home);
                    contextSymbolView.setText("H");
                    break;
                case "Work":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_work);
                    contextSymbolView.setText("W");
                    break;
                case "School":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_school);
                    contextSymbolView.setText("S");
                    break;
                case "Errands":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_errands);
                    contextSymbolView.setText("E");
                    break;
            }
            contextSymbolView.setTextColor(Color.WHITE); // Set text color to white
            contextSymbolView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // Set text size
        }

        return convertView;
    }



}
