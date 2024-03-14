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

    private GoalLists pendingList;

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
        pendingList = app.getPendingList();
        tomorrowList = app.getTomorrowList();

        storedDate = app.getStoredDate();
        recurringList = app.getRecurringList();

        currentDate.observe(this);
        DateUpdater.scheduleDateUpdates(currentDate);

        refreshAll();



    }

    public void onResume() {
        super.onResume();
        currentDate.updateTodayDate(LocalDateTime.now());
        DateUpdater.cancelDateUpdates();
        DateUpdater.scheduleDateUpdates(currentDate);

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

    public void moveToFinished(Goal goal) {
        MainViewModel.moveToFinished(goal, todayFragment.getAdapter(), todayFragment.getFinishedAdapter(), todoList);
        todayFragment.updatePlaceholderVisibility();
    }


    public void addPendingItemToTodoList(Goal goal) {
        ArrayAdapter<Goal> addAdapter = todayFragment.getPendingAdapter();
        if (pendingFragment != null) {
            addAdapter = pendingFragment.getAdapter();
        }
        MainViewModel.addItemToTodoList(goal, addAdapter, pendingList);
        if (pendingFragment != null) {
            pendingFragment.updatePlaceholderVisibility();
        }
    }

    public void deletePendingGoal(Goal goal) {
        pendingList.finishTask(goal);
        pendingList.clearFinished();
        if (pendingFragment != null) {
            pendingFragment.updatePlaceholderVisibility();
        }
        MainViewModel.deletePendingGoal(goal, PendingFragment.getAdapter());
    }

    public void addItemToTomorrowList(Goal goal) {
        ArrayAdapter<Goal> addAdapter = todayFragment.getTomorrowAdapter();
        if (tomorrowFragment != null) {
            addAdapter = tomorrowFragment.getAdapter();
        }
        MainViewModel.addItemToTodoList(goal, addAdapter, tomorrowList);
        if (tomorrowFragment != null) {
            tomorrowFragment.updatePlaceholderVisibility();
        }


    }

    public void addItemToRecurringList(RecurringGoal rgoal) {
        MainViewModel.addItemToRecurringList(rgoal, todayFragment.getAdapter(),
                todoList, tomorrowList, recurringList, currentDate.dateTime().toLocalDate());
        todayFragment.updatePlaceholderVisibility();

        if(recurringFragment != null) {
            recurringFragment.updatePlaceholderVisibility();
        }

        if(tomorrowFragment != null) {
            tomorrowFragment.updatePlaceholderVisibility();
        }

    }

    public void addItemToRecurringListTomorrow(RecurringGoal rgoal) {
        MainViewModel.addItemToRecurringListTomorrow(rgoal, tomorrowFragment.getAdapter(), tomorrowList,
                recurringList, currentDate.dateTime().toLocalDate().plusDays(1));
        tomorrowFragment.updatePlaceholderVisibility();

        if(recurringFragment != null) {
            recurringFragment.updatePlaceholderVisibility();
        }

    }

    public void deleteRecurringGoal(RecurringGoal rgoal) {
        MainViewModel.deleteRecurringGoal(rgoal, RecurringFragment.getAdapter(), recurringList);
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
                    .setReorderingAllowed(true)
                    .commitNow();

        } else if(newView == TOMORROW) {
            currentView = TOMORROW;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, tomorrowFragment == null ?
                            TomorrowFragment.newInstance() : tomorrowFragment)
                    .setReorderingAllowed(true)
                    .commitNow();
        } else if(newView == RECURRING) {
            currentView = RECURRING;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, recurringFragment == null ?
                            RecurringFragment.newInstance() : recurringFragment)
                    .setReorderingAllowed(true)
                    .commitNow();
        } else if(newView == PENDING) {
            currentView = PENDING;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, pendingFragment == null ?
                            PendingFragment.newInstance() : pendingFragment)
                    .setReorderingAllowed(true)
                    .commitNow();
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

    public void refreshAll() {
        changeView(TODAY);
        todayFragment.manualOnCreateView();
        changeView(TOMORROW);
        tomorrowFragment.manualOnCreateView();
        changeView(RECURRING);
        recurringFragment.manualOnCreateView();
        changeView(PENDING);
        pendingFragment.manualOnCreateView();
        changeView(TODAY);
    }

    //for testing
    public TodayFragment getTodayFragment() {
        return todayFragment;
    }

    public TomorrowFragment getTomorrowFragment() {
        return tomorrowFragment;
    }

    public RecurringFragment getRecurringFragment() { return recurringFragment; }

    public PendingFragment getPendingFragment() { return pendingFragment; }

    public int getCurrentView() {
        return currentView;
    }
}
