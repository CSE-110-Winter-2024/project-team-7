package edu.ucsd.cse110.successorator;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

@RunWith(AndroidJUnit4.class)
public class GoalPersistenceTest {

    // Use the InstantTaskExecutorRule to execute LiveData changes synchronously
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testPersistenceAcrossRestarts() {
        Goal goal = new Goal(null, "Persisted Task", false);
        // First launch of the activity
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                activity.addItemToTodoList(goal);
            });
        }

        // Simulate app restart by relaunching the activity
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                assertFalse("todoList should not be empty after app restart", todoList.empty());
                assertTrue("todoList should contain the persisted goal after app restart", todoList.getUnfinishedGoals().contains(goal));
            });
        }
    }
}
