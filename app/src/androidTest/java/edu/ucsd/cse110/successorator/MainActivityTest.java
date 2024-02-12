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
                assertTrue(activity.updatePlaceholderVisibility());
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }

    @Test
    public void testPlaceholderVisibilityWhenTodoListNotEmpty() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                activity.addItemToTodoList(new Goal("New Task"));
                assertFalse(activity.updatePlaceholderVisibility());
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }

    @Test
    public void testAddItemToTodoList() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                Goal newGoal = new Goal("Complete unit testing");
                activity.addItemToTodoList(newGoal);

                // Makes use of getTodoListForTesting() and getAdapterForTesting()
                // to access the private fields todoList and adapter
                GoalLists todoList = activity.getTodoListForTesting();

                assertTrue("todoList should not be empty after adding a goal", !todoList.empty());
                assertEquals("Size of todoList should be 1 after adding one goal", 1, todoList.size());
                assertEquals("First goal in the todoList should be the one that was added", newGoal, todoList.get(0));

                // Verifying the adapter has the new goal, assuming the adapter adds items at the end
                ArrayAdapter<Goal> adapter = activity.getAdapterForTesting();
                assertEquals("Adapter should contain the new goal", newGoal, adapter.getItem(adapter.getCount() - 1));
            });
        }
    }

}
