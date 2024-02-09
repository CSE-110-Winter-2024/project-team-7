package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Locale;

public class Date {

    private String formattedDate;

    public Date() {
        updateDate();
    }

    public void updateDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.US);
        String dayOfWeek = LocalDateTime.now().getDayOfWeek().name();
        String dateString = dateFormat.format(LocalDateTime.now());
        this.formattedDate = dayOfWeek + " " + dateString;
    }

}
