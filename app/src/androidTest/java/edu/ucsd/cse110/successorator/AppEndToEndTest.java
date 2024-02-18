package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import static org.junit.Assert.assertEquals;

import android.widget.TextView;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

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

                GoalLists todoList = activity.getTodoListForTesting();
                activity.moveToFinished(todoList.get(0));
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

            // Code consulted from an AI language model developed by OpenAI

            //Close the activity/Close the app
            scenario1.moveToState(Lifecycle.State.DESTROYED);
        }

        // Reopen the activity/Reopen the app
        try (var reopenedScenario = ActivityScenario.launch(MainActivity.class)) {
            reopenedScenario.onActivity(activity -> {
                assertFalse(activity.updatePlaceholderVisibility());
                assertTrue(activity.getTodoListForTesting().unfinishedSize() == 1);
                assertTrue(activity.getTodoListForTesting().get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertTrue(activity.getTodoListForTesting().finishedSize() == 1);
                assertTrue(activity.getTodoListForTesting().getFinishedGoals().get(0).toString().equals("Finish Project Report"));

                String currDate = activity.getCurrentDate().getFormattedDate();
                System.out.println(currDate);
                activity.getCurrentDate().skipDay();
                String nextDate = activity.getCurrentDate().getFormattedDate();
                System.out.println(nextDate);

                assertFalse(activity.updatePlaceholderVisibility());
                assertTrue(activity.getTodoListForTesting().unfinishedSize() == 1);
                assertTrue(activity.getTodoListForTesting().get(0).toString().equals("Prepare for Tomorrow's Presentation"));
                assertEquals(0, activity.getTodoListForTesting().finishedSize());

                GoalLists todoList = activity.getTodoListForTesting();
                activity.moveToFinished(todoList.get(0));
                todoList.clearFinished();

            });
            reopenedScenario.moveToState(Lifecycle.State.STARTED);
        }

    }
}
