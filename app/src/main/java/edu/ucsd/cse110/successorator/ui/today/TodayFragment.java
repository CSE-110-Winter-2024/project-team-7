package edu.ucsd.cse110.successorator.ui.today;

import static edu.ucsd.cse110.successorator.MainViewModel.*;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

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
import edu.ucsd.cse110.successorator.ui.tomorrow.TomorrowFragment;

public class TodayFragment extends Fragment implements Observer {
    private TodayBinding view;
    private MainActivity mainActivity;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;
    private DateHandler currentDate;
    private RoomDateStorage storedDate;
    private GoalLists todoList;
    private GoalLists tomorrowList;

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
        tomorrowList = app.getTomorrowList();
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
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        finishedAdapter = new ArrayAdapter<Goal>(this.getContext(), android.R.layout.simple_list_item_1, new ArrayList<Goal>()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textViewGoal = (TextView) view.findViewById(android.R.id.text1);
                textViewGoal.setPaintFlags(textViewGoal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                return view;
            }
        };

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
            refreshTodayAdapter(adapter, todoList);
            refreshTodayFinishedAdapter(finishedAdapter, todoList);
        }

        return isEmpty;
    }

    public void onChanged(@Nullable Object value) {
        String formattedStoredDate = storedDate.formattedDate();
        if (todoList != null && finishedAdapter != null) {
            if (!currentDate.getFormattedDate().equals(formattedStoredDate)) {
                todoList.clearFinished();
                finishedAdapter.clear();
                finishedAdapter.notifyDataSetChanged();

                SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();

                RecurringGoalLists recurringList = app.getRecurringList();

                MainViewModel.addRecurringGoalsToTodoList(recurringList, todoList, adapter, currentDate, 0);

                updatePlaceholderVisibility();
                storedDate.replace(currentDate);
            }
        }

        if (tomorrowList != null) {
            if (!currentDate.getFormattedDate().equals(formattedStoredDate)) {
                List<Goal> unfinishedGoals = tomorrowList.getUnfinishedGoals();
                for (int i = 0; i < unfinishedGoals.size(); i++) {
                    MainViewModel.addItemToTodoList(unfinishedGoals.get(i), adapter, todoList);
                }
                adapter.notifyDataSetChanged();
                tomorrowList.clearUnfinished();
                updatePlaceholderVisibility();
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
