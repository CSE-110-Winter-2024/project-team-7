package edu.ucsd.cse110.successorator;

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
import androidx.room.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.ucsd.cse110.successorator.data.db.DateDatabase;
import edu.ucsd.cse110.successorator.data.db.GoalDatabase;
import edu.ucsd.cse110.successorator.data.db.RoomDateStorage;
import edu.ucsd.cse110.successorator.data.db.RoomGoalLists;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.DateDisplay;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;
import edu.ucsd.cse110.successorator.util.DateUpdater;

public class MainActivity extends AppCompatActivity implements Observer {

    private GoalLists todoList;
    private DateHandler currentDate = new DateHandler();
    private ActivityMainBinding view;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;

    private RoomDateStorage storedDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Goal Database Setup
        var goalDatabase = Room.databaseBuilder(
                getApplicationContext(),
                GoalDatabase.class,
                "goals-database"
        ).allowMainThreadQueries().build();

        this.todoList = new RoomGoalLists(goalDatabase.goalDao());

        view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        TextView dateTextView = findViewById(R.id.date_text);
        currentDate.observe(new DateDisplay(dateTextView));
        currentDate.observe(this);
        DateUpdater.scheduleDateUpdates(currentDate);

        setupListView();
        setupDateMock();
        updatePlaceholderVisibility();

        // Date Database Setup
        var dateDatabase = Room.databaseBuilder(
                getApplicationContext(),
                DateDatabase.class,
                "date-database"
        ).allowMainThreadQueries().build();

        this.storedDate = new RoomDateStorage(dateDatabase.dateDao());

        // Checks if it's the first run so that it doesn't try to check previous date that doesn't exist
        var sharedPreferences = getSharedPreferences("goals", MODE_PRIVATE);
        var isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if(isFirstRun) {
            storedDate.replace(currentDate);
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        } else if(!currentDate.getFormattedDate().equals(storedDate.formattedDate())) {
            currentDate.updateDate(storedDate.formattedDate());
            storedDate.replace(currentDate);
        }
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
                moveToFinished(selectedItem);
            }
        });

        view.finishedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal selectedItem = finishedAdapter.getItem(position);
                moveToUnfinished(selectedItem);
            }
        });

    }

    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
        });
    }
    public void moveToFinished(Goal goal) {
        adapter.remove(goal);
        finishedAdapter.add(goal);
        adapter.notifyDataSetChanged();
        finishedAdapter.notifyDataSetChanged();
        todoList.finishTask(goal);
        updatePlaceholderVisibility();
    }

    public void moveToUnfinished(Goal goal) {
        finishedAdapter.remove(goal);
        adapter.add(goal);
        finishedAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        todoList.undoFinishTask(goal);
        updatePlaceholderVisibility();
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
        todoList.add(goal);
        adapter.add(goal);
        adapter.notifyDataSetChanged();
        updatePlaceholderVisibility();
    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = todoList.empty();
        view.goalsListView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderText.setText(R.string.default_message);
        } else {
            refreshAdapter();
            refreshFinishedAdapter();
        }

        return isEmpty;
    }

    private void refreshAdapter() {
        adapter.clear();
        adapter.addAll(todoList.getUnfinishedGoals());
        adapter.notifyDataSetChanged();
    }

    private void refreshFinishedAdapter() {
        finishedAdapter.clear();
        finishedAdapter.addAll(todoList.getFinishedGoals());
        finishedAdapter.notifyDataSetChanged();
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

    // getters for testing
    public GoalLists getTodoListForTesting() {
        return this.todoList;
    }

    public ArrayAdapter<Goal> getAdapterForTesting() {
        return this.adapter;
    }

    public ArrayAdapter<Goal> getFinishedAdapterForTesting() {
        return this.finishedAdapter;
    }

    public DateHandler getCurrentDate() {
        return currentDate;
    }
}
