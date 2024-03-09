package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import static org.junit.Assert.assertEquals;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import static edu.ucsd.cse110.successorator.MainViewModel.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SwapViewTest {
    @Test
    public void testChangeView() {
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
            });
            scenario1.moveToState(Lifecycle.State.STARTED);
        }
    }
}
