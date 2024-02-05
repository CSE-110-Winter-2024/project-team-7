package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Queue<String> todoQueue = new LinkedList<>(); // Placeholder for actual Queue
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


    // Placeholder for when addItems stories are implemented
    public void addItemToTodoList(String item) {
        // Replace this with your actual code to add an item to the to-do list
        todoQueue.add(item);

        // Update visibility after modifying the to-do list
        updatePlaceholderVisibility();
    }

    // SUBJECT TO CHANGE DEPENDING ON HOW LISTS ARE IMPLEMENTED
    public boolean updatePlaceholderVisibility() {
        boolean isVisible = todoQueue.isEmpty();
        view.placeholderText.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return isVisible;
    }
}
