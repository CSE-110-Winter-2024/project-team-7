package edu.ucsd.cse110.successorator;

import static edu.ucsd.cse110.successorator.MainViewModel.*;

import android.graphics.Paint;
import android.os.Bundle;
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

import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.ucsd.cse110.successorator.data.db.RoomDateStorage;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.DateDisplay;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;
import edu.ucsd.cse110.successorator.util.DateUpdater;

public class MainActivity extends AppCompatActivity implements Observer {

    private ActivityMainBinding view;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;

    private DateHandler currentDate;

    private GoalLists todoList;

    private RoomDateStorage storedDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        SuccessoratorApplication app = (SuccessoratorApplication) getApplication();
        currentDate = app.getCurrentDate();
        todoList = app.getTodoList();
        storedDate = app.getStoredDate();

        TextView dateTextView = findViewById(R.id.date_text);
        currentDate.observe(new DateDisplay(dateTextView));
        currentDate.observe(this);
        DateUpdater.scheduleDateUpdates(currentDate);

        setupListView();
        setupDateMock();
        updatePlaceholderVisibility();
    }

    public void onResume() {
        super.onResume();
        currentDate.updateDate(LocalDateTime.now());
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        finishedAdapter = new ArrayAdapter<Goal>(this, android.R.layout.simple_list_item_1, new ArrayList<Goal>()) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.action_bar_add_button) {
            var dialogFragment = AddGoalDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "AddGoalDialogFragment");
        }

        return super.onOptionsItemSelected(item);
    }


    public void addItemToTodoList(Goal goal) {
        MainViewModel.addItemToTodoList(goal, adapter, todoList);
        updatePlaceholderVisibility();
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

    @Override
    public void onChanged(@Nullable Object value) {
        if (todoList != null && finishedAdapter != null) {
            todoList.clearFinished();
            finishedAdapter.clear();
            finishedAdapter.notifyDataSetChanged();
            updatePlaceholderVisibility();

            storedDate.replace(currentDate);
        }
    }

    public ArrayAdapter<Goal> getAdapter() {
        return this.adapter;
    }

    public ArrayAdapter<Goal> getFinishedAdapter() {
        return this.finishedAdapter;
    }

}
