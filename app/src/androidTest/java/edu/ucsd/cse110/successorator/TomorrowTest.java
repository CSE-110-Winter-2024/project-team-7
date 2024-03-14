package edu.ucsd.cse110.successorator;


import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;


import static org.junit.Assert.assertEquals;


import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;
import edu.ucsd.cse110.successorator.ui.tomorrow.TomorrowFragment;


import static edu.ucsd.cse110.successorator.MainViewModel.*;


import android.os.Bundle;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;




//THESE TESTS MUST BE RUN SEPARATELY FOR NOW
@RunWith(AndroidJUnit4.class)
public class TomorrowTest {
    @Test
    public void testDailyOneTime() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                GoalLists tomorrowList = app.getTomorrowList();
                DateHandler currentDate = app.getCurrentDate();
                activity.addItemToTomorrowList(new Goal(1, "TestGoal", false));
                assertTrue(tomorrowList.unfinishedSize() == 1);
                assertTrue(todoList.unfinishedSize() == 0);
                currentDate.skipDay();
                assertTrue(tomorrowList.empty());
                assertTrue(todoList.unfinishedSize() == 1);
                moveToFinished(todoList.get(0), activity.getTodayFragment().getAdapter(), activity.getTodayFragment().getFinishedAdapter(), todoList);
                assertTrue(todoList.unfinishedSize() == 0);
                assertTrue(todoList.finishedSize() == 1);
                assertTrue(tomorrowList.empty());
                assertTrue(todoList.getFinishedGoals().get(0).content().equals("TestGoal"));
                currentDate.skipDay();
                assertTrue(todoList.empty());
                assertTrue(tomorrowList.empty());


            });
        }
    }


}

