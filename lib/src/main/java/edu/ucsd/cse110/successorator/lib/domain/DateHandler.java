package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {

    private String formattedDate;
    private LocalDateTime dateTime;
    private DateTimeFormatter dateFormat;

    public DateHandler() {
        this.dateFormat = DateTimeFormatter.ofPattern("MM/dd");
        this.dateTime = LocalDateTime.now();
        updateDate();
    }

    public void updateDate() {
        String dayOfWeek = dateTime.getDayOfWeek().name();
        String dateString = dateFormat.format(dateTime);
        this.formattedDate = dayOfWeek + " " + dateString;
    }

    public void skipDay() {
        this.dateTime = dateTime.plusDays(1);
        updateDate();
    }

    public String getFormattedDate() {
        return formattedDate;
    }
}
