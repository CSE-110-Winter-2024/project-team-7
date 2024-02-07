package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;

public class MainActivity extends AppCompatActivity {

    private GoalLists todoList = new GoalLists(); // Placeholder for actual Queue
    private ActivityMainBinding view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        view.placeholderText.setText(R.string.default_message);

        setContentView(view.getRoot());
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


    // PLACEHOLDER FOR WHEN ADD ITEMS STORIES ARE IMPLEMENTED
    public void addItemToTodoList(Goal goal) {
        todoList.add(goal);

        updatePlaceholderVisibility();
    }

    // SUBJECT TO CHANGE DEPENDING ON HOW LISTS ARE IMPLEMENTED
    public boolean updatePlaceholderVisibility() {
        boolean isVisible = todoList.empty();
        //view.placeholderText.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        //UNCOMMENT ABOVE AFTER UI IS IMPLEMENTED
        //BELOW IS A SUBSTITUTE FOR UI TO BE REPLACED WHEN UI IS IMPLEMENTED
        if(isVisible) {
            view.placeholderText.setText(R.string.default_message);
        } else {
            view.placeholderText.setText(todoList.toString());
        }

        return isVisible;
    }
}
