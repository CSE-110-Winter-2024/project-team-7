package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Goal goal = getItem(position);
        if (goal != null) {
            TextView goalDescriptionTextView = convertView.findViewById(R.id.goalDescriptionTextView);
            TextView contextSymbolView = convertView.findViewById(R.id.contextSymbolView);


            // Set the text and strikethrough to show the goal is finished
            goalDescriptionTextView.setText(goal.content());
            goalDescriptionTextView.setPaintFlags(goalDescriptionTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            // Set the context symbol's background color
            String contextSymbol = "";
            int contextBackground = 0;
            switch (goal.getContext()) {
                case "Home":
                    contextSymbol = "H";
                    contextBackground = R.drawable.shape_home;
                    break;
                case "Work":
                    contextSymbol = "W";
                    contextBackground = R.drawable.shape_work;
                    break;
                case "School":
                    contextSymbol = "S";
                    contextBackground = R.drawable.shape_school;
                    break;
                case "Errands":
                    contextSymbol = "E";
                    contextBackground = R.drawable.shape_errands;
                    break;
            }
            contextSymbolView.setBackgroundResource(contextBackground);
            contextSymbolView.setText(contextSymbol);
            contextSymbolView.setTextColor(Color.WHITE); // Set text color to white
            contextSymbolView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // Set text size
        }

        return convertView;
    }
}