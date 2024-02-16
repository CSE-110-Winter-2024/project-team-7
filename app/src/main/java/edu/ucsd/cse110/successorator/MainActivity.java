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

import java.util.ArrayList;

import edu.ucsd.cse110.successorator.data.db.GoalDatabase;
import edu.ucsd.cse110.successorator.data.db.RoomGoalLists;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalLists;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;

public class MainActivity extends AppCompatActivity {

    private Date currentDate = new Date();
    private GoalLists todoList; // Placeholder for actual Queue
    private ActivityMainBinding view;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var database = Room.databaseBuilder(
                getApplicationContext(),
                GoalDatabase.class,
                "goals-database"
        ).allowMainThreadQueries().build();

        this.todoList = new RoomGoalLists(database.goalDao());

        view = ActivityMainBinding.inflate(getLayoutInflater());
        view.dateText.setText(currentDate.getFormattedDate());

        setContentView(view.getRoot());

        setupListView();
        updatePlaceholderVisibility();
    }

    private void setupListView() {
        // Assuming your ListView and Goal class have proper toString() methods for display
        // We should create/implement a goallistadapter file
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
                todoList.finishTask(selectedItem);
                moveToFinished(selectedItem);
            }
        });

        view.finishedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goal selectedItem = finishedAdapter.getItem(position);
                todoList.undoFinishTask(selectedItem);
                moveToUnfinished(selectedItem);
            }
        });

    }
    public void moveToFinished(Goal goal) {
        adapter.remove(goal);
        finishedAdapter.add(goal);
        adapter.notifyDataSetChanged();
        finishedAdapter.notifyDataSetChanged();
        updatePlaceholderVisibility();
    }

    public void moveToUnfinished(Goal goal) {
        finishedAdapter.remove(goal);
        adapter.add(goal);
        finishedAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
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
        adapter.add(goal); // Add goal directly to the adapter
        adapter.notifyDataSetChanged(); // Notify the adapter to refresh the list view
        updatePlaceholderVisibility();
    }

    public boolean updatePlaceholderVisibility() {
        boolean isEmpty = todoList.empty();
        view.goalsListView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        view.placeholderText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

        if(isEmpty) {
            view.placeholderText.setText(R.string.default_message);
        } else {
            // Optionally clear the adapter and re-add all items from todoList if needed
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

    // getter for testing
    public GoalLists getTodoListForTesting() {
        return this.todoList;
    }
    // getter for testing
    public ArrayAdapter<Goal> getAdapterForTesting() {
        return this.adapter;
    }
}
