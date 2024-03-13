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
public class PendingTest {
    @Test
    public void testAddToPending() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists pendingList = app.getPendingList();
                activity.addPendingItemToTodoList(new Goal(1, "TestGoal", false));
                assertTrue(pendingList.unfinishedSize() == 1);
            });
        }
    }


}

