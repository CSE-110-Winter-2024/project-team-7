package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.time.LocalDate;
public class RecurringGoalTest {

    @Test
    public void dailyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.DAILY, febNine);
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        for(int i = 0; i < 100; i++) {
            assertTrue(rgoal.recurToday(testDate));
            testDate = testDate.plusDays(1);
        }
    }

    @Test
    public void weeklyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.WEEKLY, febNine);
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        for(int i = 0; i < 100; i++) {
            if(i % 7 == 0) {
                assertTrue(rgoal.recurToday(testDate));
            } else {
                assertFalse(rgoal.recurToday(testDate));
            }
            testDate = testDate.plusDays(1);
        }
    }

    @Test
    public void secondFridayMonthlyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.MONTHLY, febNine);
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        ArrayList<Integer> secondFridays = new ArrayList<>();
        secondFridays.add(0);
        secondFridays.add(28);
        secondFridays.add(63);
        secondFridays.add(91);
        secondFridays.add(126);

        for(int i = 0; i < 63+28+35+20; i++) {
            System.out.println(i);
            if(secondFridays.contains(i)) {
                assertTrue(rgoal.recurToday(testDate));
            } else {
                assertFalse(rgoal.recurToday(testDate));
            }
            testDate = testDate.plusDays(1);
        }
    }

    @Test
    public void fifthThursdayMonthlyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 29);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.MONTHLY, febNine);
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
                assertTrue(rgoal.recurToday(testDate));
            } else {
                assertFalse(rgoal.recurToday(testDate));
            }
            testDate = testDate.plusDays(1);
        }

        //extra test
        testDate = LocalDate.of(2024, 4, 4);
        assertTrue(rgoal.recurToday(testDate));
    }

    @Test
    public void yearlyGoalTest() {
        LocalDate febNine = LocalDate.of(2024, 2, 9);
        RecurringGoal rgoal = new RecurringGoal(null, "test", RecurringGoal.YEARLY, febNine);
        LocalDate testDate = LocalDate.of(2024, 2, 9);

        for(int i = 0; i < 364; i++) {
            testDate = testDate.plusDays(1);
            assertFalse(rgoal.recurToday(testDate));
        }
        testDate = LocalDate.of(2024, 2, 9);
        for(int i = 0; i < 25; i++) {
            assertTrue(rgoal.recurToday(testDate));
            testDate = testDate.plusYears(1);
        }
    }
}
