package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppEndToEndTest {
    @Test
    public void testEndToEnd() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                assertTrue(activity.updatePlaceholderVisibility());
                activity.addItemToTodoList(new Goal(null,"Finish Project Report", false));
                assertFalse(activity.updatePlaceholderVisibility());
                assertTrue(activity.getTodoListForTesting().unfinishedSize() == 1);
                activity.addItemToTodoList(new Goal(null, "Prepare for Tomorrow's Presentation", false));
                assertFalse(activity.updatePlaceholderVisibility());
                assertTrue(activity.getTodoListForTesting().unfinishedSize() == 2);
                activity.moveToFinished(activity.getTodoListForTesting().get(0));
                assertFalse(activity.updatePlaceholderVisibility());
                assertTrue(activity.getTodoListForTesting().unfinishedSize() == 1);
                assertTrue(activity.getTodoListForTesting().get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertTrue(activity.getTodoListForTesting().finishedSize() == 1);
                assertTrue(activity.getTodoListForTesting().getFinishedGoals().get(0).toString().equals("Finish Project Report"));
            });
            scenario1.moveToState(Lifecycle.State.STARTED);
        }
    }
}
