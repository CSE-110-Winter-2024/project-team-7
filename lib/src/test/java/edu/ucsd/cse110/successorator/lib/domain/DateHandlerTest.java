package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDateTime;

public class DateHandlerTest {

    @Test
    public void basicDateTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        DateHandler dateClass = new DateHandler();
        dateClass.updateTodayDate(febNine);
        assertEquals("FRIDAY 02/09", dateClass.getFormattedDate());
    }

    @Test
    public void dateSkipTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        DateHandler dateClass = new DateHandler();
        dateClass.updateTodayDate(febNine);
        dateClass.skipDay();
        dateClass.skipDay();
        assertEquals("SUNDAY 02/11", dateClass.getFormattedDate());
    }
}
