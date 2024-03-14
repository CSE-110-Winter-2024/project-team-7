package edu.ucsd.cse110.successorator.ui.recurring;

import static edu.ucsd.cse110.successorator.MainViewModel.refreshRecurringAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.databinding.RecurringBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class RecurringFragment extends Fragment implements Observer {

    private MainActivity mainActivity;
    private DateHandler currentDate;
    RecurringBinding view;
    private ArrayAdapter<RecurringGoal> adapter;
    private RecurringGoalLists recurringList;

    public RecurringFragment() {

    }

    public static RecurringFragment newInstance() {
        RecurringFragment fragment = new RecurringFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();
        mainActivity.setRecurringFragment(this);
        currentDate = app.getCurrentDate();
        recurringList = app.getRecurringList();

        currentDate.observe(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = RecurringBinding.inflate(inflater, container, false);
        setupListView();

        setupDateMock();
        updatePlaceholderVisibility();

        return view.getRoot();
    }

    public void manualOnCreateView() {
        view = RecurringBinding.inflate(LayoutInflater.from(getContext()));
        setupListView();

        setupDateMock();
        updatePlaceholderVisibility();
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());

        view.goalsListRecurringView.setAdapter(adapter);

        //TODO: SETUP THE HOLD THING TO DELETE RECURRING GOALS HERE

    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = recurringList.empty();
        view.goalsListRecurringView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderRecurringText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderRecurringText.setText(R.string.placeholder_recurring_text);
        } else {
             refreshRecurringAdapter(adapter, recurringList);
        }

        return isEmpty;
    }

    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
        });
    }

    public void onChanged(@Nullable Object value) {
        //TODO: might not have to do anything here, might not need to observe date
    }

    public ArrayAdapter<RecurringGoal> getAdapter() {
        return adapter;
    }
}
