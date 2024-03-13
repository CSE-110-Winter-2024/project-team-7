package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        // Get the current goal
        Goal goal = getItem(position);

        // Get references to the Button and TextView in your custom layout
        Button button = view.findViewById(R.id.button);
        TextView textView = view.findViewById(R.id.textView);

        // Set data to views
        if (goal != null) {
            textView.setText(goal.getContext());
            // Set button properties based on context
            if ("Home".equals(goal.getContext())) {
                button.setText("H");
            } else if ("Work".equals(goal.getContext())) {
                button.setText("W");
            } else if ("School".equals(goal.getContext())) {
                button.setText("S");
            } else if ("Errand".equals(goal.getContext())) {
                button.setText("E");
            } else {
                throw new IllegalArgumentException("Invalid Context: " + this.getContext());
            }

            button.setBackgroundResource(R.drawable.radio_finished);
        }

        return view;
    }
}
