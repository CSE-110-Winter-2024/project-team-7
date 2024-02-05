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

        // addItemToTodoList("New Task");   WAS USING FOR TESTING
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }


// ADD ITEM TO TODO LIST WILL NEED TO USE CODE LIKE THIS
//    private void addItemToTodoList(String item) {
//        // Replace this with your actual code to add an item to the to-do list
//        todoQueue.add(item);
//
//        // Update visibility after modifying the to-do list
//        updatePlaceholderVisibility();
//    }

    // SUBJECT TO CHANGE DEPENDING ON HOW LISTS ARE IMPLEMENTED
    private void updatePlaceholderVisibility() {
        if (todoQueue.isEmpty()) {
            view.placeholderText.setVisibility(View.VISIBLE);
        } else {
            view.placeholderText.setVisibility(View.GONE);
        }
    }
}
