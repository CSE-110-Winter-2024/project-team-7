package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import static org.junit.Assert.assertEquals;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;

import static edu.ucsd.cse110.successorator.MainViewModel.*;


//THESE TESTS MUST BE RUN SEPARATELY FOR NOW
@RunWith(AndroidJUnit4.class)
public class RecurringTest {
    @Test
    public void testDailyRecurring() {

        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                RecurringGoalLists recurringList = app.getRecurringList();
                DateHandler currentDate = app.getCurrentDate();

                activity.addItemToRecurringList(new RecurringGoal(null,"daily",
                        RecurringGoal.DAILY, currentDate.dateTime().toLocalDate()));
                System.out.println(todoList.unfinishedSize());
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(recurringList.size() == 1);
                currentDate.skipDay();
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(recurringList.size() == 1);
                moveToFinished(todoList.get(0), activity.getAdapter(), activity.getFinishedAdapter(), todoList);
                assertTrue(todoList.unfinishedSize() == 0);
                assertTrue(todoList.finishedSize() == 1);
                assertTrue(todoList.getFinishedGoals().get(0).toString().equals("daily"));
                currentDate.skipDay();
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(todoList.getUnfinishedGoals().get(0).toString().equals("daily"));
                assertTrue(todoList.finishedSize() == 0);
                assertTrue(recurringList.size() == 1);

            });
        }
    }

    @Test
    public void testWeeklyRecurring() {

        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                RecurringGoalLists recurringList = app.getRecurringList();
                DateHandler currentDate = app.getCurrentDate();

                activity.addItemToRecurringList(new RecurringGoal(null, "weekly",
                        RecurringGoal.WEEKLY, currentDate.dateTime().toLocalDate()));
                System.out.println(todoList.unfinishedSize());
                assertTrue(todoList.unfinishedSize() == 1);
                moveToFinished(todoList.get(0), activity.getAdapter(), activity.getFinishedAdapter(), todoList);

                for(int i = 0; i < 7; i++) {
                    currentDate.skipDay();
                    if(i == 6) {
                        assertTrue(todoList.unfinishedSize() == 1);
                        assertTrue(todoList.getUnfinishedGoals().get(0).toString().equals("weekly"));
                    } else {
                        assertTrue(todoList.unfinishedSize() == 0);
                        assertTrue(todoList.finishedSize() == 0);
                    }
                }
            });
        }
    }
}
