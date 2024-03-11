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

import edu.ucsd.cse110.successorator.data.db.date.RoomDateStorage;


import edu.ucsd.cse110.successorator.lib.domain.DateHandler;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.ui.DateDisplay;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.AddPendingGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.AddRecurringGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.AddTomorrowGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.DropDownDialogFragment;
import edu.ucsd.cse110.successorator.ui.pending.PendingFragment;
import edu.ucsd.cse110.successorator.ui.recurring.RecurringFragment;
import edu.ucsd.cse110.successorator.ui.today.TodayFragment;
import edu.ucsd.cse110.successorator.ui.tomorrow.TomorrowFragment;
import edu.ucsd.cse110.successorator.util.DateUpdater;

public class MainActivity extends AppCompatActivity implements Observer {
    public static int TODAY = 0, TOMORROW = 1, RECURRING = 2, PENDING = 3;

    private ActivityMainBinding view;
    private ArrayAdapter<Goal> adapter;
    private ArrayAdapter<Goal> finishedAdapter;

    private DateHandler currentDate;

    private GoalLists todoList;

    private GoalLists tomorrowList;

    private RecurringGoalLists recurringList;

    private RoomDateStorage storedDate;

    private TodayFragment todayFragment;
    private TomorrowFragment tomorrowFragment;
    private RecurringFragment recurringFragment;
    private PendingFragment pendingFragment;

    private int currentView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        SuccessoratorApplication app = (SuccessoratorApplication) getApplication();
        currentDate = app.getCurrentDate();
        todoList = app.getTodoList();
        tomorrowList = app.getTomorrowList();
        storedDate = app.getStoredDate();
        recurringList = app.getRecurringList();

        currentDate.observe(this);
        DateUpdater.scheduleDateUpdates(currentDate);

        currentView = TODAY;

    }

    public void onResume() {
        super.onResume();
        currentDate.updateTodayDate(LocalDateTime.now());
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
            if(currentView == TODAY) {
                var dialogFragment = AddGoalDialogFragment.newInstance();
                dialogFragment.setCurrentDate(currentDate);
                dialogFragment.show(getSupportFragmentManager(), "AddTodayGoalDialogFragment");
            } else if(currentView == TOMORROW) {
                var dialogFragment = AddTomorrowGoalDialogFragment.newInstance();
                dialogFragment.setCurrentDate(currentDate);
                dialogFragment.show(getSupportFragmentManager(), "AddTomorrowGoalDialogFragment");
            } else if(currentView == RECURRING) {
                var dialogFragment = AddRecurringGoalDialogFragment.newInstance();
                dialogFragment.setCurrentDate(currentDate);
                dialogFragment.show(getSupportFragmentManager(), "AddRecurringGoalDialogFragment");
            } else if(currentView == PENDING) {
                var dialogFragment = AddPendingGoalDialogFragment.newInstance();
                //dialogFragment.setCurrentDate(currentDate);
                dialogFragment.show(getSupportFragmentManager(), "AddPendingGoalDialogFragment");
            }
        }

        if (itemId == R.id.action_arrow_drop_down_button) {
            swapFragments();
        }

        return super.onOptionsItemSelected(item);
    }


    public void addItemToTodoList(Goal goal) {
        MainViewModel.addItemToTodoList(goal, todayFragment.getAdapter(), todoList);
        todayFragment.updatePlaceholderVisibility();
    }

    public void addItemToTomorrowList(Goal goal) {
        MainViewModel.addItemToTodoList(goal, tomorrowFragment.getAdapter(), tomorrowList);
        tomorrowFragment.updatePlaceholderVisibility();
    }

    public void addItemToRecurringList(RecurringGoal rgoal) {
        MainViewModel.addItemToRecurringList(rgoal, todayFragment.getAdapter(), todoList,
                recurringList, currentDate.dateTime().toLocalDate());
        todayFragment.updatePlaceholderVisibility();
        recurringFragment.updatePlaceholderVisibility();
    }

    @Override
    public void onChanged(@Nullable Object value) {
        if (todoList != null && todayFragment.getAdapter() != null) {
            //moved to todayfragment for now
            //MainViewModel.addRecurringGoalsToTodoList(recurringList, todoList, todayFragment.getAdapter(), currentDate);
        }
    }

    private void swapFragments() {
        var dialogFragment = DropDownDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "dropdown_fragment");
    }

    public void changeView(int newView) {
        if(newView == TODAY) {
            currentView = TODAY;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, todayFragment == null ?
                            TodayFragment.newInstance() : todayFragment)
                    .commit();
        } else if(newView == TOMORROW) {
            currentView = TOMORROW;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, tomorrowFragment == null ?
                            TomorrowFragment.newInstance() : tomorrowFragment)
                    .commit();
        } else if(newView == RECURRING) {
            currentView = RECURRING;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, recurringFragment == null ?
                            RecurringFragment.newInstance() : recurringFragment)
                    .commit();
        } else if(newView == PENDING) {
            currentView = PENDING;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, pendingFragment == null ?
                            PendingFragment.newInstance() : pendingFragment)
                    .commit();
        }
    }

    public ArrayAdapter<Goal> getAdapter() {
        return this.adapter;
    }

    public ArrayAdapter<Goal> getFinishedAdapter() {
        return this.finishedAdapter;
    }

    public DateHandler getCurrentDate() {
        return currentDate;
    }

    public GoalLists getTodoList() {
        return todoList;
    }

    public void setTodayFragment(TodayFragment todayFragment) {
        this.todayFragment = todayFragment;
    }

    public void setTomorrowFragment(TomorrowFragment tommorowFragment) {
        this.tomorrowFragment = tommorowFragment;
    }

    public void setRecurringFragment(RecurringFragment recurringFragment) {
        this.recurringFragment = recurringFragment;
    }

    public void setPendingFragment(PendingFragment pendingFragment) {
        this.pendingFragment = pendingFragment;
    }

    //for testing
    public TodayFragment getTodayFragment() {
        return todayFragment;
    }

    public int getCurrentView() {
        return currentView;
    }
}
