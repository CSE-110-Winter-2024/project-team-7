package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Date {

    private String formattedDate;

    public Date() {
        updateDate();
    }

    public void updateDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd");
        String dayOfWeek = LocalDateTime.now().getDayOfWeek().name();
        String dateString = dateFormat.format(LocalDateTime.now());
        this.formattedDate = dayOfWeek + " " + dateString;
    }

//    public void updateDate(LocalDateTime dateTime) {
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd");
//        String dayOfWeek = LocalDateTime.now().getDayOfWeek().name();
//        String dateString = dateFormat.format(LocalDateTime.now());
//        this.formattedDate = dayOfWeek + " " + dateString;
//    }

    public String getFormattedDate() {
        return formattedDate;
    }
}
