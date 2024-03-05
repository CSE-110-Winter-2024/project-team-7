package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import edu.ucsd.cse110.successorator.data.db.date.RoomDateStorage;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

public class MainViewModel extends ViewModel implements Observer {

    private final RoomDateStorage storedDate;
    private final GoalLists todoList;
    private final DateHandler currentDate;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getStoredDate(), app.getTodoList(), app.getCurrentDate());
                    });

    public MainViewModel(RoomDateStorage storedDate, GoalLists todoList, DateHandler currentDate) {
        this.storedDate = storedDate;
        this.todoList = todoList;
        this.currentDate = currentDate;
    }

    public static void moveToFinished(Goal goal, ArrayAdapter<Goal> adapter, ArrayAdapter<Goal> finishedAdapter, GoalLists todoList) {
        adapter.remove(goal);
        finishedAdapter.add(goal);
        adapter.notifyDataSetChanged();
        finishedAdapter.notifyDataSetChanged();
        todoList.finishTask(goal);
    }

    public static void moveToUnfinished(Goal goal, ArrayAdapter<Goal> adapter, ArrayAdapter<Goal> finishedAdapter, GoalLists todoList) {
        finishedAdapter.remove(goal);
        adapter.add(goal);
        finishedAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        todoList.undoFinishTask(goal);
    }

    public static void addItemToTodoList(Goal goal, ArrayAdapter<Goal> adapter, GoalLists todoList) {
        todoList.add(goal);
        adapter.add(goal);
        adapter.notifyDataSetChanged();
    }

    public static void refreshAdapter(ArrayAdapter<Goal> adapter, GoalLists todoList) {
        adapter.clear();
        adapter.addAll(todoList.getUnfinishedGoals());
        adapter.notifyDataSetChanged();
    }

    public static void refreshFinishedAdapter(ArrayAdapter<Goal> finishedAdapter, GoalLists todoList) {
        finishedAdapter.clear();
        finishedAdapter.addAll(todoList.getFinishedGoals());
        finishedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChanged(Object o) {

    }
}
