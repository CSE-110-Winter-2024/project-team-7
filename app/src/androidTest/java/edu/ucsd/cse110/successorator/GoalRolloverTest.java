package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalRolloverTest {

    @Test
    public void testRolloverOnChanged() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                activity.addItemToTodoList(new Goal(null, "Goal 1", false));
                activity.addItemToTodoList(new Goal(null, "Goal 2", false));

                activity.moveToFinished(activity.getTodoListForTesting().get(0));
                assertEquals(1, activity.getTodoListForTesting().unfinishedSize());
                assertEquals(1, activity.getTodoListForTesting().finishedSize());

                activity.getCurrentDate().updateDate(LocalDateTime.now().plusDays(1));


                assertEquals(1, activity.getTodoListForTesting().unfinishedSize());
                assertEquals(0, activity.getTodoListForTesting().finishedSize());
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}

