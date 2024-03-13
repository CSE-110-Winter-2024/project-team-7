package edu.ucsd.cse110.successorator.ui.today;

import static edu.ucsd.cse110.successorator.MainViewModel.*;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.data.db.date.RoomDateStorage;
import edu.ucsd.cse110.successorator.databinding.TodayBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.DateDisplay;
import edu.ucsd.cse110.successorator.util.DateUpdater;
import edu.ucsd.cse110.successorator.util.GoalArrayAdapter;
import edu.ucsd.cse110.successorator.util.GoalFinishedArrayAdapter;

public class TodayFragment extends Fragment implements Observer {
    private TodayBinding view;
    private MainActivity mainActivity;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;
    private DateHandler currentDate;
    private RoomDateStorage storedDate;

    private GoalLists todoList;

    public TodayFragment() {

    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();
        mainActivity.setTodayFragment(this);
        currentDate = app.getCurrentDate();
        todoList = app.getTodoList();
        storedDate = app.getStoredDate();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = TodayBinding.inflate(inflater, container, false);
        setupListView();
        TextView dateTextView = view.dateText;
        currentDate.observe(new DateDisplay(dateTextView));
        currentDate.observe(this);
        setupDateMock();
        updatePlaceholderVisibility();

        return view.getRoot();
    }

    private void setupListView() {
        adapter = new GoalArrayAdapter(this.getContext(), R.layout.goals_with_context_list_view_items, new ArrayList<>());

//        adapter = new ArrayAdapter<>(this.getContext(), R.layout.goals_with_context_list_view_items, new ArrayList<>()) {
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                Button button = view.findViewById(R.id.button);
//
//                var goal = getItem(position).getContext();
//                if (goal == "Home") {
//                    button.setText("H");
//                    button.setBackgroundResource(R.drawable.radio_h);
//                } else if (goal == "Work") {
//                    button.setText("W");
//                    button.setBackgroundResource(R.drawable.radio_w);
//                } else if (goal == "School") {
//                    button.setText("S");
//                    button.setBackgroundResource(R.drawable.radio_s);
//                } else if (goal == "Errand") {
//                    button.setText("E");
//                    button.setBackgroundResource(R.drawable.radio_e);
//                } else {
//                    throw new IllegalArgumentException("Invalid Context: " + goal);
//                }
//
//                return view;
//            }
//        };

        finishedAdapter = new GoalFinishedArrayAdapter(this.getContext(), R.layout.goals_with_context_list_view_items, new ArrayList<Goal>());

//        finishedAdapter = new ArrayAdapter<Goal>(this.getContext(), R.layout.goals_with_context_list_view_items, new ArrayList<Goal>()) {
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView textViewGoal = (TextView) view.findViewById(android.R.id.text1);
//                textViewGoal.setPaintFlags(textViewGoal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                Button button = view.findViewById(R.id.button);
//
//                var goal = getItem(position).getContext();
//                if (goal == "Home") {
//                    button.setText("H");
//                } else if (goal == "Work") {
//                    button.setText("W");
//                } else if (goal == "School") {
//                    button.setText("S");
//                } else if (goal == "Errand") {
//                    button.setText("E");
//                } else {
//                    throw new IllegalArgumentException("Invalid Context: " + goal);
//                }
//
//                button.setBackgroundResource(R.drawable.radio_finished);
//                return view;
//            }
//        };

        view.goalsListView.setAdapter(adapter);
        view.finishedListView.setAdapter(finishedAdapter);
        view.goalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal selectedItem = adapter.getItem(position);
                moveToFinished(selectedItem, adapter, finishedAdapter, todoList);
                updatePlaceholderVisibility();
            }
        });

        view.finishedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal selectedItem = finishedAdapter.getItem(position);
                moveToUnfinished(selectedItem, adapter, finishedAdapter, todoList);
                updatePlaceholderVisibility();
            }
        });

    }

    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
        });
    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = todoList.empty();
        view.goalsListView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderText.setText(R.string.default_message);
        } else {
            refreshAdapter(adapter, todoList);
            refreshFinishedAdapter(finishedAdapter, todoList);
        }

        return isEmpty;
    }

    public void onChanged(@Nullable Object value) {
        if (todoList != null && finishedAdapter != null) {
            if (!currentDate.getFormattedDate().equals(storedDate.formattedDate())) {
                todoList.clearFinished();
                finishedAdapter.clear();
                finishedAdapter.notifyDataSetChanged();

                SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();

                RecurringGoalLists recurringList = app.getRecurringList();

                MainViewModel.addRecurringGoalsToTodoList(recurringList, todoList, adapter, currentDate);

                updatePlaceholderVisibility();
                storedDate.replace(currentDate);
            }
        }
    }

    public ArrayAdapter<Goal> getAdapter() {
        return adapter;
    }

    public ArrayAdapter<Goal> getFinishedAdapter() {
        return finishedAdapter;
    }


}
