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

import java.time.LocalDate;
import java.time.LocalDateTime;


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
                        RecurringGoal.DAILY, currentDate.dateTime().toLocalDate(), ""));
                System.out.println(todoList.unfinishedSize());
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(recurringList.size() == 1);
                currentDate.skipDay();
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(recurringList.size() == 1);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
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
                        RecurringGoal.WEEKLY, currentDate.dateTime().toLocalDate(), ""));
                System.out.println(todoList.unfinishedSize());
                assertTrue(todoList.unfinishedSize() == 1);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);

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

    @Test
    public void testMonthlyRecurring() {

        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                RecurringGoalLists recurringList = app.getRecurringList();
                DateHandler currentDate = app.getCurrentDate();

                currentDate.updateTodayDate(LocalDateTime.of(2024, 3, 5, 1, 1));

                activity.addItemToRecurringList(new RecurringGoal(null, "monthly",
                        RecurringGoal.MONTHLY, currentDate.dateTime().toLocalDate(), ""));
                System.out.println(todoList.unfinishedSize());
                assertTrue(todoList.unfinishedSize() == 1);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);

                for(int i = 0; i < 30; i++) {
                    currentDate.skipDay();
                    if(i >= 27) {
                        assertTrue(todoList.unfinishedSize() == 1);
                        assertTrue(todoList.getUnfinishedGoals().get(0).toString().equals("monthly"));
                    } else {
                        assertTrue(todoList.unfinishedSize() == 0);
                        assertTrue(todoList.finishedSize() == 0);
                    }
                }
            });
        }
    }

    @Test
    public void testYearlyRecurring() {

        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                RecurringGoalLists recurringList = app.getRecurringList();
                DateHandler currentDate = app.getCurrentDate();

                currentDate.updateTodayDate(LocalDateTime.of(2024, 3, 5, 1, 1));

                activity.addItemToRecurringList(new RecurringGoal(null, "yearly",
                        RecurringGoal.YEARLY, currentDate.dateTime().toLocalDate(), ""));
                System.out.println(todoList.unfinishedSize());
                assertTrue(todoList.unfinishedSize() == 1);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);

                for(int i = 0; i < 365; i++) {
                    currentDate.skipDay();
                    if(i == 364) {
                        assertTrue(todoList.unfinishedSize() == 1);
                        assertTrue(todoList.getUnfinishedGoals().get(0).toString().equals("yearly"));
                    } else {
                        assertTrue(todoList.unfinishedSize() == 0);
                        assertTrue(todoList.finishedSize() == 0);
                    }
                }
            });
        }
    }

    @Test
    public void testDeleteRecurring() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                DateHandler currentDate = app.getCurrentDate();
                RecurringGoalLists recurringList = app.getRecurringList();
                activity.addItemToRecurringList(new RecurringGoal(null,"daily",
                                RecurringGoal.DAILY, currentDate.dateTime().toLocalDate(), ""));
                assertTrue(recurringList.size() == 1);
                RecurringGoal selected = recurringList.get(0);
                activity.deleteRecurringGoal(selected);
                assertTrue(recurringList.size() == 0);
            });
        }
    }

}
