package edu.ucsd.cse110.successorator.ui.tomorrow;

import static edu.ucsd.cse110.successorator.MainViewModel.moveToFinished;
import static edu.ucsd.cse110.successorator.MainViewModel.moveToUnfinished;
import static edu.ucsd.cse110.successorator.MainViewModel.refreshTodayAdapter;
import static edu.ucsd.cse110.successorator.MainViewModel.refreshTodayFinishedAdapter;

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
import edu.ucsd.cse110.successorator.databinding.TomorrowBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.util.GoalArrayAdapter;
import edu.ucsd.cse110.successorator.util.GoalFinishedArrayAdapter;

public class TomorrowFragment extends Fragment implements Observer {
    private MainActivity mainActivity;
    private DateHandler currentDate;
    private RoomDateStorage storedDate;
    private String formattedStoredDate;
    private TextView dateTextView;
    TomorrowBinding view;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;

    private ArrayAdapter<Goal> todayAdapter;
    private GoalLists tomorrowList;



    private GoalLists todayList;


    public TomorrowFragment() {

    }

    public static TomorrowFragment newInstance() {
        TomorrowFragment fragment = new TomorrowFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();
        mainActivity.setTomorrowFragment(this);
        currentDate = app.getCurrentDate();
        tomorrowList = app.getTomorrowList();
        todayList = app.getTodoList();
        storedDate = app.getStoredDate();
        formattedStoredDate = storedDate.formattedDate();
        todayAdapter = mainActivity.getTodayFragment().getAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = TomorrowBinding.inflate(inflater, container, false);
        setupListView();
        dateTextView = view.dateTomorrowText;
        dateTextView.setText(currentDate.getTomorrowDate());
        if(!currentDate.getObservers().contains(this)) {
            currentDate.observe(this);
        }
        setupDateMock();
        updatePlaceholderVisibility();
        System.out.println("herererereerererere");

        return view.getRoot();
    }

    public void manualOnCreateView() {
        view = TomorrowBinding.inflate(LayoutInflater.from(getContext()));
        setupListView();
        dateTextView = view.dateTomorrowText;
        dateTextView.setText(currentDate.getTomorrowDate());
        if(!currentDate.getObservers().contains(this)) {
            currentDate.observe(this);
        }
        setupDateMock();
        updatePlaceholderVisibility();
        System.out.println("herererereerererere");
    }

    private void setupListView() {
        adapter = new GoalArrayAdapter(this.getContext(), R.layout.list_item_goal, new ArrayList<>());
        finishedAdapter = new GoalFinishedArrayAdapter(this.getContext(), R.layout.list_item_goal, new ArrayList<Goal>());

        view.goalsListTomorrowView.setAdapter(adapter);
        view.finishedListTomorrowView.setAdapter(finishedAdapter);
        view.goalsListTomorrowView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal selectedItem = adapter.getItem(position);
                moveToFinished(selectedItem, adapter, finishedAdapter, tomorrowList);
                updatePlaceholderVisibility();
            }
        });

        view.finishedListTomorrowView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal selectedItem = finishedAdapter.getItem(position);
                moveToUnfinished(selectedItem, adapter, finishedAdapter, tomorrowList);
                updatePlaceholderVisibility();
            }
        });
    }


    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = tomorrowList.empty();
        view.goalsListTomorrowView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderTomorrowText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderTomorrowText.setText(R.string.placeholder_tomorrow_text);
        } else {
            refreshTodayAdapter(adapter, tomorrowList);
            refreshTodayFinishedAdapter(finishedAdapter, tomorrowList);
        }

        return isEmpty;
    }

    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
            updatePlaceholderVisibility();
        });
    }

    public void onChanged(@Nullable Object value) {
        if(dateTextView != null) {
            dateTextView.setText(currentDate.getTomorrowDate());
        }

        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        if (tomorrowList != null && finishedAdapter != null) {
            if (!currentDate.getFormattedDate().equals(formattedStoredDate)) {
                tomorrowList.clearFinished();
                finishedAdapter.clear();
                finishedAdapter.notifyDataSetChanged();

                SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();

                RecurringGoalLists recurringList = app.getRecurringList();

                MainViewModel.addRecurringGoalsToTodoList(recurringList, tomorrowList, adapter, currentDate, 1);

                updatePlaceholderVisibility();
                if (mainActivity.getTomorrowFragment() != null) {
                    mainActivity.getTomorrowFragment().updatePlaceholderVisibility();
                }
                storedDate.replace(currentDate);
            }
        }


    }

    public ArrayAdapter<Goal> getAdapter() {
        return adapter;
    }
}
