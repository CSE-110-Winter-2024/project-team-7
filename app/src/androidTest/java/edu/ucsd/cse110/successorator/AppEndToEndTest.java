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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppEndToEndTest {
    @Test
    public void testEndToEnd1() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                assertTrue(activity.getTodayFragment().updatePlaceholderVisibility());
                activity.addItemToTodoList(new Goal(null,"Finish Project Report", false));
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 1);
                activity.addItemToTodoList(new Goal(null, "Prepare for Tomorrow's Presentation", false));
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 2);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(todoList.get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertTrue(todoList.finishedSize() == 1);
                assertTrue(todoList.getFinishedGoals().get(0).toString().equals("Finish Project Report"));

                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                todoList.clearFinished();
            });
            scenario1.moveToState(Lifecycle.State.STARTED);
        }
    }

    //END-TO-END TEST FOR ITERATION 2
    @Test
    public void testEndToEnd2() {

        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                assertTrue(activity.getTodayFragment().updatePlaceholderVisibility());
                activity.addItemToTodoList(new Goal(null,"Finish Project Report", false));
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 1);
                activity.addItemToTodoList(new Goal(null, "Prepare for Tomorrow's Presentation", false));
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 2);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(todoList.get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertTrue(todoList.finishedSize() == 1);
                assertTrue(todoList.getFinishedGoals().get(0).toString().equals("Finish Project Report"));
            });

            // Code consulted from an AI language model developed by OpenAI

            //Close the activity/Close the app
            scenario1.moveToState(Lifecycle.State.DESTROYED);
        }

        // Reopen the activity/Reopen the app
        try (var reopenedScenario = ActivityScenario.launch(MainActivity.class)) {
            reopenedScenario.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                String currentDate = app.getCurrentDate().getFormattedDate();
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(todoList.get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertTrue(todoList.finishedSize() == 1);
                assertTrue(todoList.getFinishedGoals().get(0).toString().equals("Finish Project Report"));

                System.out.println(currentDate);
                app.getCurrentDate().skipDay();
                String nextDate = app.getCurrentDate().getFormattedDate();
                System.out.println(nextDate);

                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());
                assertTrue(todoList.unfinishedSize() == 1);
                assertTrue(todoList.get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertEquals(0, todoList.finishedSize());

                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                todoList.clearFinished();

            });
            reopenedScenario.moveToState(Lifecycle.State.STARTED);
        }

    }

    @Test
    public void testEndToEnd3() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                assertTrue(activity.getCurrentView() == MainActivity.TODAY);

                activity.changeView(MainActivity.TOMORROW);
                assertTrue(activity.getCurrentView() == MainActivity.TOMORROW);

                activity.changeView(MainActivity.RECURRING);
                assertTrue(activity.getCurrentView() == MainActivity.RECURRING);

                activity.changeView(MainActivity.PENDING);
                assertTrue(activity.getCurrentView() == MainActivity.PENDING);

                activity.changeView(MainActivity.TODAY);
                assertTrue(activity.getCurrentView() == MainActivity.TODAY);

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
            scenario1.moveToState(Lifecycle.State.STARTED);
        }
    }
}
