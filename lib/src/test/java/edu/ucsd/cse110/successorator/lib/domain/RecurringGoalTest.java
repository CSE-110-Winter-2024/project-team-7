package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.time.LocalDate;
public class RecurringGoalTest {

    @Test
    public void dailyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.DAILY, febNine, "Home");
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        for(int i = 0; i < 100; i++) {
            assertTrue(rgoal.recurToday(testDate));
            testDate = testDate.plusDays(1);
        }
    }

    @Test
    public void weeklyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.WEEKLY, febNine, "Home");
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        for (int i = 0; i < 100; i++) {
            if (i % 7 == 0) {
                assertTrue("Expected to recur today: " + testDate, rgoal.recurToday(testDate));
            } else {
                assertFalse("Expected not to recur today: " + testDate, rgoal.recurToday(testDate));
            }
            testDate = testDate.plusDays(1);
        }
    }


    @Test
    public void secondFridayMonthlyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.MONTHLY, febNine, "Home");
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        ArrayList<Integer> secondFridays = new ArrayList<>();
        secondFridays.add(0);
        secondFridays.add(28);
        secondFridays.add(63);
        secondFridays.add(91);
        secondFridays.add(126);

        for (int i = 0; i < 63 + 28 + 35 + 20; i++) {
            System.out.println(i);
            if (secondFridays.contains(i)) {
                assertTrue("Expected to recur today: " + testDate, rgoal.recurToday(testDate));
            } else {
                assertFalse("Expected not to recur today: " + testDate, rgoal.recurToday(testDate));
            }
            testDate = testDate.plusDays(1);
        }
    }


    @Test
    public void fifthThursdayMonthlyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 29);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.MONTHLY, febNine, "Home");
        LocalDate testDate = LocalDate.of(2024, 2, 29);

        ArrayList<Integer> fifthThursday = new ArrayList<>();
        fifthThursday.add(0);
        fifthThursday.add(35);
        fifthThursday.add(63);
        fifthThursday.add(91);
        fifthThursday.add(126);

        for(int i = 0; i < 63+28+35+20; i++) {
            System.out.println(i);
            if(fifthThursday.contains(i)) {
                assertTrue("Expected to recur today: " + testDate, rgoal.recurToday(testDate));
            } else {
                assertFalse("Expected not to recur today: " + testDate, rgoal.recurToday(testDate));
            }
            testDate = testDate.plusDays(1);
        }

    }


    @Test
    public void yearlyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        // Adding a context parameter to the constructor call, e.g., "Anniversary"
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.YEARLY, febNine, "Home");
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        assertTrue(rgoal.recurToday(febNine));

        // Test for all days in a year except Feb 9, should not recur
        for(int i = 0; i < 364; i++) {
            testDate = testDate.plusDays(1);
            assertFalse("Expected not to recur today: " + testDate, rgoal.recurToday(testDate));
        }

        // Reset testDate to Feb 9, 2024, for yearly recurrence test
        testDate = LocalDate.of(2024, 2, 9);

        // Test for the next 25 years, should recur on Feb 9 each year
        for(int i = 0; i < 25; i++) {
            assertTrue("Expected to recur today: " + testDate, rgoal.recurToday(testDate));
            testDate = testDate.plusYears(1);
        }
    }

    @Test
    public void skipPastDayTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.WEEKLY, febNine, "");
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        assertTrue(rgoal.recurToday(febNine));

        testDate = testDate.plusDays(3);
        assertFalse(rgoal.recurToday(testDate));

        //skips past the day here
        testDate = testDate.plusWeeks(1);
        assertTrue(rgoal.recurToday(testDate));

        //goes back to the day that was skipped
        testDate = testDate.plusDays(-3);
        assertFalse(rgoal.recurToday(testDate));

        testDate = testDate.plusWeeks(1);
        assertTrue(rgoal.recurToday(testDate));
    }

    @Test
    public void testRecurringGoalsInDifferentContexts() {
        LocalDate startDate = LocalDate.of(2024, 2, 9);
        RecurringGoal homeGoal = new RecurringGoal(null, "Clean the house", RecurringGoal.WEEKLY, startDate, "Home");
        RecurringGoal workGoal = new RecurringGoal(null, "Weekly meeting", RecurringGoal.WEEKLY, startDate, "Work");

        assertTrue("Expected home goal to recur today", homeGoal.recurToday(startDate));
        assertTrue("Expected work goal to recur today", workGoal.recurToday(startDate));
    }

    @Test
    public void testRecurringGoalsAcrossContexts() {
        LocalDate startDate = LocalDate.of(2024, 2, 9);
        RecurringGoal schoolGoal = new RecurringGoal(null, "Math class", RecurringGoal.DAILY, startDate, "School");
        RecurringGoal errandsGoal = new RecurringGoal(null, "Grocery shopping", RecurringGoal.MONTHLY, startDate, "Errands");

        assertTrue("Expected school goal to recur today", schoolGoal.recurToday(startDate.plusDays(1)));
        assertFalse("Errands goal should not recur today", errandsGoal.recurToday(startDate.plusDays(1)));
    }

    @Test
    public void testRecurringGoalsDoNotRecurOutsideContext() {
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        RecurringGoal errandsGoal = new RecurringGoal(null, "Pharmacy visit", RecurringGoal.MONTHLY, startDate, "Errands");

        assertFalse("Errands goal should not recur today", errandsGoal.recurToday(LocalDate.of(2024, 3, 2)));
    }

    @Test
    public void yearlyRecurringGoalWithContextTest() {
        LocalDate startDate = LocalDate.of(2024, 5, 20);
        RecurringGoal anniversaryGoal = new RecurringGoal(null, "Anniversary", RecurringGoal.YEARLY, startDate, "Home");

        assertTrue("Expected anniversary goal to recur today", anniversaryGoal.recurToday(LocalDate.of(2025, 5, 20)));
        assertFalse("Anniversary goal should not recur today", anniversaryGoal.recurToday(LocalDate.of(2025, 5, 21)));
    }


}
