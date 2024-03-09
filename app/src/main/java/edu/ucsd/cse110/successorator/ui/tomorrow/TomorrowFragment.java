package edu.ucsd.cse110.successorator.ui.tomorrow;

import static edu.ucsd.cse110.successorator.MainViewModel.moveToFinished;
import static edu.ucsd.cse110.successorator.MainViewModel.moveToUnfinished;
import static edu.ucsd.cse110.successorator.MainViewModel.refreshAdapter;
import static edu.ucsd.cse110.successorator.MainViewModel.refreshFinishedAdapter;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.databinding.TodayBinding;
import edu.ucsd.cse110.successorator.databinding.TomorrowBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.DateDisplay;
import edu.ucsd.cse110.successorator.ui.dialog.AddTomorrowGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.DropDownDialogFragment;
import edu.ucsd.cse110.successorator.ui.today.TodayFragment;
import edu.ucsd.cse110.successorator.util.DateUpdater;

public class TomorrowFragment extends Fragment implements Observer {
    private MainActivity mainActivity;
    private DateHandler currentDate;
    private TextView dateTextView;
    TomorrowBinding view;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;
    //THIS MAY BE TEMPORARY IF WE WANT TO MAKE A TOMORROWLIST CLASS
    private GoalLists tomorrowList;


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
        //tomorrowList = app.getTomorrowList(); NOT YET IMPLEMENTED
        tomorrowList = app.getTodoList(); //TO AVOID CRASHES

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = TomorrowBinding.inflate(inflater, container, false);
        dateTextView = view.dateTomorrowText;
        dateTextView.setText(currentDate.getTomorrowDate());
        currentDate.observe(this);

        setupListView();
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
            refreshAdapter(adapter, tomorrowList);
            refreshFinishedAdapter(finishedAdapter, tomorrowList);
        }

        return isEmpty;
    }

    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
        });
    }

    public void onChanged(@Nullable Object value) {
        dateTextView.setText(currentDate.getTomorrowDate());
        //TODO: rollover all tomorrow goals to today, and delete(i think?) the finished goals
    }

    public ArrayAdapter<Goal> getAdapter() {
        return adapter;
    }
}