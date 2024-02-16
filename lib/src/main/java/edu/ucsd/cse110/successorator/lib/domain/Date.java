package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Date {

    private String formattedDate;
    private LocalDateTime dateTime;
    private DateTimeFormatter dateFormat;

    public Date() {
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
