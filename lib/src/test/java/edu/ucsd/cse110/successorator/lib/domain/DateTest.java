package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTest {

    @Test
    public void dateTest() {
        LocalDateTime febNine = LocalDateTime.of(2024, 2, 9, 2, 3);
        DateHandler dateClass = new DateHandler();
        dateClass.updateDate();
        LocalDateTime expected = LocalDateTime.now();
        String dayOfWeek = expected.getDayOfWeek().name();
        String dateString = expected.format(DateTimeFormatter.ofPattern("MM/dd"));
        String expectedDate = dayOfWeek + " " + dateString;
        assertEquals(expectedDate, dateClass.getFormattedDate());

    }
}
