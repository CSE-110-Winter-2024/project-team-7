package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTest {

    @Test
    public void dateTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        Date dateClass = new Date();
        dateClass.updateDate(febNine);
        System.out.println(dateClass.getFormattedDate());
        assertEquals(dateClass.getFormattedDate(), "FRIDAY 02/09");

    }
}
