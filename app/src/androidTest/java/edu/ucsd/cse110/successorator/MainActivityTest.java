package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

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

}
