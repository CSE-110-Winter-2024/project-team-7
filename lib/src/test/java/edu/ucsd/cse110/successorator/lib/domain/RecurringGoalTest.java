package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.time.LocalDateTime;
public class RecurringGoalTest {

    @Test
    public void dailyGoalTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        RecurringGoal rgoal = new RecurringGoal("test", RecurringGoal.DAILY, febNine);
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 9, 2, 3);

        for(int i = 0; i < 100; i++) {
            assertTrue(rgoal.recurToday(testDate));
            testDate = testDate.plusDays(1);
        }
    }

    @Test
    public void weeklyGoalTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        RecurringGoal rgoal = new RecurringGoal("test", RecurringGoal.WEEKLY, febNine);
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 9, 2, 3);

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
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        RecurringGoal rgoal = new RecurringGoal("test", RecurringGoal.MONTHLY, febNine);
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 9, 2, 3);

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
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 29, 2, 3);
        RecurringGoal rgoal = new RecurringGoal("test", RecurringGoal.MONTHLY, febNine);
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 29, 2, 3);

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
        testDate = LocalDateTime.of(2024, 4, 4, 2, 3);
        assertTrue(rgoal.recurToday(testDate));
    }

    @Test
    public void yearlyGoalTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        RecurringGoal rgoal = new RecurringGoal("test", RecurringGoal.YEARLY, febNine);
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 9, 2, 3);

        for(int i = 0; i < 364; i++) {
            testDate = testDate.plusDays(1);
            assertFalse(rgoal.recurToday(testDate));
        }
        testDate = LocalDateTime.of(2024, 2, 9, 2, 3);
        for(int i = 0; i < 25; i++) {
            assertTrue(rgoal.recurToday(testDate));
            testDate = testDate.plusYears(1);
        }
    }
}
