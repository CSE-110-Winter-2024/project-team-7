package edu.ucsd.cse110.successorator;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.DateDatabase;
import edu.ucsd.cse110.successorator.data.db.GoalDatabase;
import edu.ucsd.cse110.successorator.data.db.RoomDateStorage;
import edu.ucsd.cse110.successorator.data.db.RoomGoalLists;

public class Successorator {
    private RoomDateStorage storedDate;

    public void initializeDatabases(MainActivity mainActivity) {
        GoalDatabase goalDatabase = Room.databaseBuilder(
                mainActivity.getApplicationContext(),
                GoalDatabase.class,
                "goals-database"
        ).allowMainThreadQueries().build();

        mainActivity.todoList = new RoomGoalLists(goalDatabase.goalDao());

        DateDatabase dateDatabase = Room.databaseBuilder(
                mainActivity.getApplicationContext(),
                DateDatabase.class,
                "date-database"
        ).allowMainThreadQueries().build();

        storedDate = new RoomDateStorage(dateDatabase.dateDao());

        checkFirstRun(mainActivity);
    }

    private void checkFirstRun(MainActivity mainActivity) {
        android.content.SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("goals", MainActivity.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            storedDate.replace(mainActivity.currentDate);
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        } else if (!mainActivity.currentDate.getFormattedDate().equals(storedDate.formattedDate())) {
            mainActivity.currentDate.updateDate(storedDate.formattedDate());
            storedDate.replace(mainActivity.currentDate);
        }
    }
}
