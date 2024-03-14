package edu.ucsd.cse110.successorator.ui.recurring;

import static edu.ucsd.cse110.successorator.MainViewModel.refreshRecurringAdapter;
import static edu.ucsd.cse110.successorator.MainViewModel.refreshRecurringAdapterByContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import edu.ucsd.cse110.successorator.util.RecurringGoalArrayAdapter;
import edu.ucsd.cse110.successorator.ui.dialog.DeleteRecurringGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.MovePendingGoalDialogFragment;

public class RecurringFragment extends Fragment implements Observer {

    private MainActivity mainActivity;
    private DateHandler currentDate;
    RecurringBinding view;
    private static ArrayAdapter<RecurringGoal> adapter;
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
        adapter = new RecurringGoalArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());

        view.goalsListRecurringView.setAdapter(adapter);

        view.goalsListRecurringView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                var dialogFragment = DeleteRecurringGoalDialogFragment.newInstance(position);
                dialogFragment.show(mainActivity.getSupportFragmentManager(), "DeleteRecurringGoalDialogFragment");
                updatePlaceholderVisibility();
                return true;
            }
        });

    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = recurringList.empty();
        view.goalsListRecurringView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderRecurringText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderRecurringText.setText(R.string.placeholder_recurring_text);
        } else {
            if (mainActivity.getFocus() == "All") {
                refreshRecurringAdapter(adapter, recurringList);
            }
            else {
                refreshRecurringAdapterByContext(adapter, recurringList, mainActivity.getFocus());
            }
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

    public static ArrayAdapter<RecurringGoal> getAdapter() {
        return adapter;
    }
}
