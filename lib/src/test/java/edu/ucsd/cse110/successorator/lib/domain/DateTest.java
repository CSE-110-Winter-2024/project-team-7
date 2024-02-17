package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTest {

    @Test
    public void basicDateTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        DateHandler dateClass = new DateHandler();
        dateClass.updateDate(febNine);
        assertEquals("FRIDAY 02/09", dateClass.getFormattedDate());
    }

    @Test
    public void dateSkipTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        DateHandler dateClass = new DateHandler();
        dateClass.updateDate(febNine);
        dateClass.skipDay();
        dateClass.skipDay();
        assertEquals("SUNDAY 02/11", dateClass.getFormattedDate());
    }
}
