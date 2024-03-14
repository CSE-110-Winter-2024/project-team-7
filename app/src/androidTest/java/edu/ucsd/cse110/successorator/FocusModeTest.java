package edu.ucsd.cse110.successorator;

import static junit.framework.TestCase.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

public class FocusModeTest {
    @Test
    public void testFocus() {
        try (var scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.onActivity(activity -> {
                SuccessoratorApplication app = (SuccessoratorApplication) activity.getApplication();
                GoalLists todoList = app.getTodoList();
                Goal goal1 = new Goal(null, "New Task", false, false, "Home");
                Goal goal2 = new Goal(null, "New Task", false, false, "Work");
                Goal goal3 = new Goal(null, "New Task", false, false, "School");
                Goal goal4 = new Goal(null, "New Task", false, false, "Errands");
                activity.addItemToTodoList(goal1);
                activity.addItemToTodoList(goal2);
                activity.addItemToTodoList(goal3);
                activity.addItemToTodoList(goal4);
                assertTrue(todoList.unfinishedSize() == 4);
                activity.setFocus("Home");
                assertTrue(activity.getTodayFragment().getAdapter().getItem(0).toString().equals(goal1.toString()));
                activity.setFocus("Work");
                assertTrue(activity.getTodayFragment().getAdapter().getItem(0).toString().equals(goal2.toString()));
                activity.setFocus("School");
                assertTrue(activity.getTodayFragment().getAdapter().getItem(0).toString().equals(goal3.toString()));
                activity.setFocus("Errands");
                assertTrue(activity.getTodayFragment().getAdapter().getItem(0).toString().equals(goal4.toString()));
                activity.setFocus("All");
                assertTrue(activity.getTodayFragment().getAdapter().getItem(0).toString().equals(goal1.toString()));
            });
            scenario1.moveToState(Lifecycle.State.STARTED);
        }
    }
}
