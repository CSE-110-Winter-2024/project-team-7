package edu.ucsd.cse110.successorator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.successorator.util.GoalArrayAdapter;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ContextTests {
    private GoalArrayAdapter goalArrayAdapter;
    private Goal testGoal;
    private View view;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        goalArrayAdapter = new GoalArrayAdapter(context, R.layout.list_item_goal, new ArrayList<Goal>());

        // Set up the test goals
        Goal workGoal = new Goal(1, "Test goal", false, false, "Work");
        Goal homeGoal = new Goal(2, "Home goal", false, false, "Home");
        Goal schoolGoal = new Goal(3, "School goal", false, false, "School");
        Goal errandsGoal = new Goal(4, "Errands goal", false, false, "Errands");

        // Add them to the adapter
        goalArrayAdapter.add(workGoal);
        goalArrayAdapter.add(homeGoal);
        goalArrayAdapter.add(schoolGoal);
        goalArrayAdapter.add(errandsGoal);

        // Use the correct index when calling getView
        view = goalArrayAdapter.getView(0, null, null);
    }

    @Test
    public void testGoalContext_Work() {
        view = goalArrayAdapter.getView(0, null, null);
        TextView contextSymbolView = view.findViewById(R.id.contextSymbolView);
        assertNotNull("Context symbol view should not be null", contextSymbolView);
        assertEquals("Context symbol should be 'W' for work", "W", contextSymbolView.getText().toString());
    }

    @Test
    public void testGoalContext_Home() {
        view = goalArrayAdapter.getView(1, null, null);
        TextView contextSymbolView = view.findViewById(R.id.contextSymbolView);
        assertNotNull("Context symbol view should not be null", contextSymbolView);
        assertEquals("Context symbol should be 'H' for home", "H", contextSymbolView.getText().toString());
    }

    @Test
    public void testGoalContext_School() {
        view = goalArrayAdapter.getView(2, null, null);
        TextView contextSymbolView = view.findViewById(R.id.contextSymbolView);
        assertNotNull("Context symbol view should not be null", contextSymbolView);
        assertEquals("Context symbol should be 'S' for school", "S", contextSymbolView.getText().toString());
    }

    @Test
    public void testGoalContext_Errands() {
        view = goalArrayAdapter.getView(3, null, null);
        TextView contextSymbolView = view.findViewById(R.id.contextSymbolView);
        assertNotNull("Context symbol view should not be null", contextSymbolView);
        assertEquals("Context symbol should be 'E' for errands", "E", contextSymbolView.getText().toString());
    }


}
