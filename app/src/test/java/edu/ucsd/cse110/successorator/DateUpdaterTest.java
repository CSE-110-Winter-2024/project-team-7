package edu.ucsd.cse110.successorator;

import org.junit.Test;
import java.util.Calendar;
import java.util.Timer;

import static org.junit.Assert.*;

import edu.ucsd.cse110.successorator.util.DateUpdater;

public class DateUpdaterTest {

    @Test
    public void testCalculateDelayToMidnight() {
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY, 23);
        currentTime.set(Calendar.MINUTE, 59);
        currentTime.set(Calendar.SECOND, 59);

        long delay = DateUpdater.calculateDelayToMidnight(currentTime);

        Calendar midnight = (Calendar) currentTime.clone();
        midnight.add(Calendar.DAY_OF_YEAR, 1);
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        long expectedDelay = midnight.getTimeInMillis() - currentTime.getTimeInMillis();

        assertTrue(Math.abs(delay - expectedDelay) < 1000);
    }

    @Test
    public void testCancelDateUpdates() {
        // Ensure that timer is initially null
        assertNull(getTimerField());

        // Call cancelDateUpdates
        DateUpdater.cancelDateUpdates();

        // Ensure that timer is null after cancellation
        assertNull(getTimerField());
    }

    // Helper method to access the timer field using reflection
    private Timer getTimerField() {
        try {
            java.lang.reflect.Field field = DateUpdater.class.getDeclaredField("timer");
            field.setAccessible(true);
            return (Timer) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
