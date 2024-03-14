package edu.ucsd.cse110.successorator.ui.pending;

import static edu.ucsd.cse110.successorator.MainViewModel.moveToFinished;
import static edu.ucsd.cse110.successorator.MainViewModel.refreshTodayAdapter;

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
import edu.ucsd.cse110.successorator.databinding.PendingBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.dialog.DropDownDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.MovePendingGoalDialogFragment;
import edu.ucsd.cse110.successorator.util.GoalArrayAdapter;

public class PendingFragment extends Fragment{

    private MainActivity mainActivity;
    PendingBinding view;
    private GoalLists pendingList;
    private DateHandler currentDate;
    private static ArrayAdapter<Goal> adapter;


    public PendingFragment() {

    }

    public static PendingFragment newInstance() {
        PendingFragment fragment = new PendingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        SuccessoratorApplication app = (SuccessoratorApplication) mainActivity.getApplication();
        currentDate = app.getCurrentDate();
        mainActivity.setPendingFragment(this);
        pendingList = app.getPendingList();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = PendingBinding.inflate(inflater, container, false);

        setupListView();
        setupDateMock();
        updatePlaceholderVisibility();

        return view.getRoot();
    }

    public void manualOnCreateView() {
        view = PendingBinding.inflate(LayoutInflater.from(getContext()));
        setupListView();
        updatePlaceholderVisibility();
    }

    private void setupListView() {
        adapter = new GoalArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());

        view.goalsListPendingView.setAdapter(adapter);

        view.goalsListPendingView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                var dialogFragment = MovePendingGoalDialogFragment.newInstance(position);
                dialogFragment.show(mainActivity.getSupportFragmentManager(), "MovePendingGoalDialogFragment");
                updatePlaceholderVisibility();
                return true;
            }
        });

    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = pendingList.empty();
        view.goalsListPendingView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderPendingText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderPendingText.setText(R.string.placeholder_pending_text);
        } else {
            refreshTodayAdapter(adapter, pendingList);
        }
        return isEmpty;
    }


    public void onChanged(@Nullable Object value) {
        //TODO: might not have to do anything here, might not need to observe date
    }


    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
        });
    }


    public static ArrayAdapter<Goal> getAdapter() {
        return adapter;
    }
}
