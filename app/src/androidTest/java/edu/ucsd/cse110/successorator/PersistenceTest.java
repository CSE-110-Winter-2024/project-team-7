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

@RunWith(AndroidJUnit4.class)
public class PersistenceTest {
    @Test
    public void testCreateGoalThenExit() {
        Goal newGoal = new Goal(null, "Persistence Test", false);
        //first launch of the app
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                activity.addItemToTodoList(newGoal);

                GoalLists todoList = activity.getTodoListForTesting();

                assertTrue("todoList should not be empty after adding a goal", !todoList.empty());
                assertEquals("Size of todoList should be 1 after adding one goal", 1, todoList.size());
                assertEquals("First goal in the todoList should be the one that was added", newGoal, todoList.get(0));

                ArrayAdapter<Goal> adapter = activity.getAdapterForTesting();
                assertEquals("Adapter should contain the new goal", newGoal, adapter.getItem(adapter.getCount() - 1));
            });
        }
        //second launch of the app should still have the goal
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                GoalLists todoList = activity.getTodoListForTesting();

                //all same tests should still work even with no add called in this activity
                assertTrue("todoList should not be empty after adding a goal", !todoList.empty());
                assertEquals("Size of todoList should be 1 after adding one goal", 1, todoList.size());
                assertEquals("First goal in the todoList should be the one that was added", newGoal, todoList.get(0));

                ArrayAdapter<Goal> adapter = activity.getAdapterForTesting();
                assertEquals("Adapter should contain the new goal", newGoal, adapter.getItem(adapter.getCount() - 1));
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}
