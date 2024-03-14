package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import edu.ucsd.cse110.successorator.data.db.date.RoomDateStorage;
import edu.ucsd.cse110.successorator.data.db.recurringgoal.RecurringGoalEntity;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainViewModel extends ViewModel implements Observer {

    private final RoomDateStorage storedDate;
    private final GoalLists todoList;
    private final GoalLists tomorrowList;
    private final DateHandler currentDate;

    private final RecurringGoalLists recurringList;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getStoredDate(), app.getTodoList(), app.getTomorrowList(), app.getCurrentDate(), app.getRecurringList());
                    });

    public MainViewModel(RoomDateStorage storedDate, GoalLists todoList, GoalLists tomorrowList, DateHandler currentDate, RecurringGoalLists recurringList) {
        this.storedDate = storedDate;
        this.todoList = todoList;
        this.tomorrowList = tomorrowList;
        this.currentDate = currentDate;
        this.recurringList = recurringList;
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
        if(adapter != null) {
            adapter.add(goal);
            adapter.notifyDataSetChanged();
        }
    }


    //UPDATE THIS WHEN THE RECURRING LIST UI IS CREATED TO UPDATE THE UI
    //OR DO THAT IN A SEPARATE METHOD
    public static void addItemToRecurringList(RecurringGoal rgoal, ArrayAdapter<Goal> todayAdapter, GoalLists todoList,
                                              GoalLists tomorrowList, RecurringGoalLists recurringList,
                                              LocalDate todayDate) {
        int id = recurringList.add(rgoal);

        if(rgoal.recurToday(todayDate)) {
            addItemToTodoList(rgoal.toGoal(), todayAdapter, todoList);
            rgoal.setId(id);
            recurringList.add(rgoal);
        }
        if(rgoal.recurToday(todayDate.plusDays(1))) {
            tomorrowList.add(rgoal.toGoal());
            rgoal.setId(id);
            recurringList.add(rgoal);
        }
    }

    public static void addItemToRecurringListTomorrow(RecurringGoal rgoal, ArrayAdapter<Goal> todayAdapter,
                                              GoalLists tomorrowList, RecurringGoalLists recurringList,
                                              LocalDate tomorrowDate) {
        int id = recurringList.add(rgoal);

        if(rgoal.recurToday(tomorrowDate)) {
            addItemToTodoList(rgoal.toGoal(), todayAdapter, tomorrowList);
            rgoal.setId(id);
            recurringList.add(rgoal);
        }
    }

    public static void addRecurringGoalsToTodoList(RecurringGoalLists recurringList, GoalLists todoList,
                                                   ArrayAdapter<Goal> adapter, DateHandler currentDate, int tomorrowOffset) {

        List<RecurringGoal> recurringGoals = recurringList.getRecurringGoals();
        LocalDate compareDate = currentDate.dateTime().toLocalDate().plusDays(tomorrowOffset);

        List<Goal> unfinished = todoList.getUnfinishedGoals();
        for(RecurringGoal rgoal : recurringGoals) {
            if(rgoal.recurToday(compareDate)) {
                //DOESN'T ADD THE GOAL IF THERE IS A GOAL WITH THE SAME TEXT
                //BUT HYPOTHETICALLY A PERSON COULD MAKE TWO DIFFERENT GOALS WITH SAME TEXT
                boolean alreadyExists = false;
                for(Goal g : unfinished) {
                    if(g.isFromRecurring() && g.content().equals(rgoal.content()))
                        alreadyExists = true;
                }
                if(!alreadyExists) {
                    addItemToTodoList(rgoal.toGoal(), adapter, todoList);
                }
                recurringList.add(rgoal);
            }
        }
    }

    public static void refreshTodayAdapter(ArrayAdapter<Goal> adapter, GoalLists todoList) {
        List<Goal> sortedGoalsStepOne =
                Stream.concat(todoList.getUnfinishedGoalsByContext("Home").stream(), todoList.getUnfinishedGoalsByContext("Work").stream()).collect(Collectors.toList()); ;
        List<Goal> sortedGoalsStepTwo =
                Stream.concat(sortedGoalsStepOne.stream(), todoList.getUnfinishedGoalsByContext("School").stream()).collect(Collectors.toList()); ;
        List<Goal> sortedGoals =
                Stream.concat(sortedGoalsStepTwo.stream(), todoList.getUnfinishedGoalsByContext("Errands").stream()).collect(Collectors.toList()); ;
        adapter.clear();
        adapter.addAll(sortedGoals);
        adapter.notifyDataSetChanged();
    }


    public static void refreshTodayFinishedAdapter(ArrayAdapter<Goal> finishedAdapter, GoalLists todoList) {
        List<Goal> sortedGoalsStepOne =
                Stream.concat(todoList.getFinishedGoalsByContext("Home").stream(), todoList.getFinishedGoalsByContext("Work").stream()).collect(Collectors.toList()); ;
        List<Goal> sortedGoalsStepTwo =
                Stream.concat(sortedGoalsStepOne.stream(), todoList.getFinishedGoalsByContext("School").stream()).collect(Collectors.toList()); ;
        List<Goal> sortedGoals =
                Stream.concat(sortedGoalsStepTwo.stream(), todoList.getFinishedGoalsByContext("Errands").stream()).collect(Collectors.toList()); ;
        finishedAdapter.clear();
        finishedAdapter.addAll(sortedGoals);
        finishedAdapter.notifyDataSetChanged();
    }

    public static void refreshRecurringAdapter(ArrayAdapter<RecurringGoal> adapter, RecurringGoalLists recurringList) {
        List<RecurringGoal> sortedGoalsStepOne =
                Stream.concat(recurringList.getRecurringGoalsByContext("Home").stream(), recurringList.getRecurringGoalsByContext("Work").stream()).collect(Collectors.toList()); ;
        List<RecurringGoal> sortedGoalsStepTwo =
                Stream.concat(sortedGoalsStepOne.stream(), recurringList.getRecurringGoalsByContext("School").stream()).collect(Collectors.toList()); ;
        List<RecurringGoal> sortedGoals =
                Stream.concat(sortedGoalsStepTwo.stream(), recurringList.getRecurringGoalsByContext("Errands").stream()).collect(Collectors.toList());
        adapter.clear();
        adapter.addAll(sortedGoals);
        adapter.notifyDataSetChanged();
    }

    public static void deletePendingGoal(Goal goal, ArrayAdapter<Goal> adapter) {
        adapter.remove(goal);
        adapter.notifyDataSetChanged();
    }

    public static void deleteRecurringGoal(RecurringGoal rgoal, ArrayAdapter<RecurringGoal> adapter, RecurringGoalLists recurringList) {
        adapter.remove(rgoal);
        adapter.notifyDataSetChanged();
        recurringList.delete(rgoal);
    }
    @Override
    public void onChanged(Object o) {

    }
}
