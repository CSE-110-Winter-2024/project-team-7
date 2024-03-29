package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.date.DateDatabase;
import edu.ucsd.cse110.successorator.data.db.standardgoal.GoalDatabase;
import edu.ucsd.cse110.successorator.data.db.date.RoomDateStorage;
import edu.ucsd.cse110.successorator.data.db.standardgoal.RoomGoalLists;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

import edu.ucsd.cse110.successorator.data.db.recurringgoal.RecurringGoalDatabase;
import edu.ucsd.cse110.successorator.data.db.recurringgoal.RoomRecurringGoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;

public class SuccessoratorApplication extends Application {
    private RoomDateStorage storedDate;

    private final DateHandler currentDate = new DateHandler();

    private GoalLists todoList;


    private GoalLists pendingList;

    private GoalLists tomorrowList;


    private RecurringGoalLists recurringList;

    @Override
    public void onCreate() {
        super.onCreate();


        // Goal Database Setup
        var goalDatabase = Room.databaseBuilder(
                getApplicationContext(),
                GoalDatabase.class,
                "goals-database"
        ).allowMainThreadQueries().build();

        this.todoList = new RoomGoalLists(goalDatabase.goalDao());

        // Tomorrow Database Setup
        var tomorrowGoalDatabase = Room.databaseBuilder(
                getApplicationContext(),
                GoalDatabase.class,
                "tomorrow-goals-database"
        ).allowMainThreadQueries().build();

        this.tomorrowList = new RoomGoalLists(tomorrowGoalDatabase.goalDao());

        //Recurring Goal Database Setup
        var recurringDatabase = Room.databaseBuilder(
                getApplicationContext(),
                RecurringGoalDatabase.class,
                "recurring-database"
        ).allowMainThreadQueries().build();

        this.recurringList = new RoomRecurringGoalLists(recurringDatabase.rgoalDao());

        //Pending Goal Database Setup
        var pendingDatabase = Room.databaseBuilder(
                getApplicationContext(),
                GoalDatabase.class,
                "pending-database"
        ).allowMainThreadQueries().build();

        this.pendingList = new RoomGoalLists(pendingDatabase.goalDao());

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
            currentDate.updateTodayDate(storedDate.formattedDate());
        }


    }

    public DateHandler getCurrentDate() {
        return currentDate;
    }

    public GoalLists getTodoList() {
        return todoList;
    }

    public GoalLists getTomorrowList() {
        return tomorrowList;
    }

    public RoomDateStorage getStoredDate() {
        return storedDate;
    }

    public RecurringGoalLists getRecurringList() {
        return recurringList;
    }

    public GoalLists getPendingList() { return pendingList; }

}
