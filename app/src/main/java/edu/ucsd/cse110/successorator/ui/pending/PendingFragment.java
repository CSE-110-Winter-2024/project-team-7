package edu.ucsd.cse110.successorator.ui.pending;

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
import edu.ucsd.cse110.successorator.databinding.PendingBinding;
import edu.ucsd.cse110.successorator.databinding.RecurringBinding;
import edu.ucsd.cse110.successorator.databinding.TomorrowBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.DropDownDialogFragment;
import edu.ucsd.cse110.successorator.ui.tomorrow.TomorrowFragment;

public class PendingFragment extends Fragment implements Observer {

    private MainActivity mainActivity;
    private DateHandler currentDate;
    PendingBinding view;
    private ArrayAdapter<Goal> adapter;
    private GoalLists pendingList; //might need its own class

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
        mainActivity.setPendingFragment(this);
        currentDate = app.getCurrentDate();
        //pendingList = app.getRecurringList(); NOT YET IMPLEMENTED
        pendingList = app.getTodoList(); //TEMPORARY TO AVOID CRASHES

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = PendingBinding.inflate(inflater, container, false);
        setupListView();
        currentDate.observe(this);
        setupDateMock();
        updatePlaceholderVisibility();

        return view.getRoot();
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());

        view.goalsListPendingView.setAdapter(adapter);

        //TODO: SETUP THE HOLD THING TO DELETE RECURRING GOALS HERE

    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = pendingList.empty();
        view.goalsListPendingView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderPendingText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderPendingText.setText(R.string.placeholder_pending_text);
        } else {
            //refreshAdapter(adapter, recurringList); //WRITE REFRESH ADAPTER THAT TAKES RECURRINGLIST ARGUMENT
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

    public ArrayAdapter<Goal> getAdapter() {
        return adapter;
    }
}
