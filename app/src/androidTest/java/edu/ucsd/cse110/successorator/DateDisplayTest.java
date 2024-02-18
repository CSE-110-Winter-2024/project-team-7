package edu.ucsd.cse110.successorator;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.util.Log;
import android.widget.TextView;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.ui.DateDisplay;

public class DateDisplayTest {

    @Test
    public void testDateOnChanged() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                TextView dateTextView = activity.findViewById(R.id.date_text);
                assertNotNull("TextView not found", dateTextView);

                DateDisplay dateDisplay = new DateDisplay(dateTextView);
                String sampleDate = "Monday 01/01";
                dateDisplay.onChanged(sampleDate);

                assertEquals(sampleDate, dateTextView.getText().toString());
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}



