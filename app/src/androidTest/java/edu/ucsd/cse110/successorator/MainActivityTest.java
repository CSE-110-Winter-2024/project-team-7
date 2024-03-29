package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import static org.junit.Assert.assertEquals;

import android.widget.ArrayAdapter;

import static edu.ucsd.cse110.successorator.MainViewModel.*;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test
    public void testPlaceholderVisibilityWhenTodoListEmpty() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                assertTrue(activity.getTodayFragment().updatePlaceholderVisibility());
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }

    @Test
    public void testPlaceholderVisibilityWhenTodoListNotEmpty() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                Goal newGoal = new Goal(null, "New Task", false);
                activity.addItemToTodoList(newGoal);
                assertFalse(activity.getTodayFragment().updatePlaceholderVisibility());


                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                todoList.clearFinished();
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }

    @Test
    public void testAddItemToTodoList() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                Goal newGoal = new Goal(null, "Complete unit testing", false);
                activity.addItemToTodoList(newGoal);

                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();

                assertTrue("todoList should not be empty after adding a goal", !todoList.empty());
                assertEquals("Size of todoList should be 1 after adding one goal", 1, todoList.size());
                assertEquals("First goal in the todoList should be the one that was added", newGoal.content(), todoList.get(0).content());

                ArrayAdapter<Goal> adapter = activity.getTodayFragment().getAdapter();
                assertEquals("Adapter should contain the new goal", newGoal.content(), adapter.getItem(adapter.getCount() - 1).content());

                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                todoList.clearFinished();
            });
        }
    }

}
