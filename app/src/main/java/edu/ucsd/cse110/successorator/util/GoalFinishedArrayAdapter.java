package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import android.util.Log;


import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalFinishedArrayAdapter extends ArrayAdapter<Goal> {
    private static final String TAG = "GoalFinishedAdapter";
    private Context mContext;
    private int mResource;

    public GoalFinishedArrayAdapter(Context context, int resource, List<Goal> goals) {
        super(context, resource, goals);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView called for position: " + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            Log.d(TAG, "Inflating new view for position: " + position);
        }

        Goal goal = getItem(position);
        if (goal != null) {
            TextView goalDescriptionTextView = convertView.findViewById(R.id.goalDescriptionTextView);
            View contextSymbolView = convertView.findViewById(R.id.contextSymbolView);

            if (goalDescriptionTextView == null) {
                Log.e(TAG, "goalDescriptionTextView is null!");
            }
            if (contextSymbolView == null) {
                Log.e(TAG, "contextSymbolView is null!");
            }

            // Set the text and strikethrough to show the goal is finished
            goalDescriptionTextView.setText(goal.content());
            goalDescriptionTextView.setPaintFlags(goalDescriptionTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // Set the context symbol's background color
            switch (goal.getContext()) {
                case "Home":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_home);
                    Log.d(TAG, "Setting context symbol to Home");
                    break;
                case "Work":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_work);
                    Log.d(TAG, "Setting context symbol to Work");
                    break;
                case "School":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_school);
                    Log.d(TAG, "Setting context symbol to School");
                    break;
                case "Errands":
                    contextSymbolView.setBackgroundResource(R.drawable.shape_errands);
                    Log.d(TAG, "Setting context symbol to Errands");
                    break;
            }
        } else {
            Log.d(TAG, "Goal is null for position: " + position);
        }

        return convertView;
    }
}
