package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import static edu.ucsd.cse110.successorator.MainViewModel.*;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalRolloverTest {

    @Test
    public void testRolloverSkip() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                DateHandler currentDate = app.getCurrentDate();
                activity.addItemToTodoList(new Goal(null, "Goal 1", false));
                activity.addItemToTodoList(new Goal(null, "Goal 2", false));

                moveToFinished(app.getTodoList().get(0), activity.getAdapter(), activity.getFinishedAdapter(), app.getTodoList());
                assertEquals(1, app.getTodoList().unfinishedSize());
                assertEquals(1, app.getTodoList().finishedSize());

                currentDate.updateDate(LocalDateTime.now().plusDays(1));


                assertEquals(1, app.getTodoList().unfinishedSize());
                assertEquals(0, app.getTodoList().finishedSize());
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}

