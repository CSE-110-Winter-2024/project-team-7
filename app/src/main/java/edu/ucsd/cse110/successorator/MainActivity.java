package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;

public class MainActivity extends AppCompatActivity {

    private Date currentDate = new Date();
    private GoalLists todoList = new GoalLists(); // Placeholder for actual Queue
    private ActivityMainBinding view;
    private ArrayAdapter<Goal> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = ActivityMainBinding.inflate(getLayoutInflater());

        //view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        //view.placeholderText.setText(R.string.default_message);
        view.dateText.setText(currentDate.getFormattedDate());

        setContentView(view.getRoot());

        setupListView();
        updatePlaceholderVisibility();
    }

    private void setupListView() {
        // Assuming your ListView and Goal class have proper toString() methods for display
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        view.goalsListView.setAdapter(adapter);
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
        }

        return isEmpty;
    }

    private void refreshAdapter() {
        adapter.clear();
        for (int i = 0; i < todoList.size(); i++) {
            adapter.add(todoList.get(i));
        }
        adapter.notifyDataSetChanged();
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
