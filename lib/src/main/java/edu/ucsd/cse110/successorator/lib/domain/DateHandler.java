package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class DateHandler implements Subject {

    private String formattedDate;
    private LocalDateTime dateTime;
    private DateTimeFormatter dateFormat;
    private final List<Observer> observers = new ArrayList<>();

    public DateHandler() {
        dateFormat = DateTimeFormatter.ofPattern("EEE M/dd");
        this.dateTime = null;
        updateTodayDate((String) null);
    }

    public void updateTodayDate(String previousDate) {
        this.dateTime = LocalDateTime.now();
        LocalTime currentTime = dateTime.toLocalTime();
        if (currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.of(2, 0))) {
            dateTime = dateTime.minusDays(1);
        }
        String newFormattedDate = "Today, " + dateTime.format(dateFormat);
        if (formattedDate == null || !(formattedDate.equals(newFormattedDate)) || !(newFormattedDate.equals(previousDate))) {
            this.formattedDate = newFormattedDate;
            notifyObservers();
        }
    }

    public void updateTodayDate(LocalDateTime dateInput) {
        this.dateTime = dateInput;

        LocalTime currentTime = dateTime.toLocalTime();
        if (currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.of(2, 0))) {
            dateTime = dateTime.minusDays(1);
            System.out.println("datehandler print: " + dateTime);
        }

        String newFormattedDate = "Today, " + dateTime.format(dateFormat);

        if (formattedDate == null || !(formattedDate.equals(newFormattedDate))) {
            this.formattedDate = newFormattedDate;
            notifyObservers();

        }
        System.out.println("datehandler print: " + formattedDate);
    }

    public void skipDay() {
        this.dateTime = dateTime.plusDays(1);
        updateTodayDate(dateTime);

    }

    public String getTomorrowDate() {
        LocalDateTime tomorrow = this.dateTime.plusDays(1);
        return "Tomorrow, " + tomorrow.format(dateFormat);
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    @Nullable
    @Override
    public Object getValue() {
        return dateTime;
    }

    @Override
    public void observe(Observer observer) {
        observers.add(observer);
        observer.onChanged(formattedDate);
        System.out.println(observers.size());
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.onChanged(formattedDate);
        }
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    public String monthDay() {
        return dateFormat.format(dateTime);
    }

    public String getMonthAndDate(long offset){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d");
        return dateTime.plusDays(offset).format(formatter);
    }

    public String getWeekdayInMonth(long offset){
        DayOfWeek dayOfWeek = dateTime.plusDays(offset).getDayOfWeek();

        // Get the ordinal value for the day of the week (e.g., 3rd Tuesday)
        int ordinal = (dateTime.getDayOfMonth() - 1) / 7 + 1;

        // Get the textual representation of the day of the week and the ordinal value
        String ordinalSuffix;
        switch (ordinal) {
            case 1:
                ordinalSuffix = "st";
                break;
            case 2:
                ordinalSuffix = "nd";
                break;
            case 3:
                ordinalSuffix = "rd";
                break;
            default:
                ordinalSuffix = "th";
        }

        String dayOfWeekText = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

        return ordinal + ordinalSuffix + " " + dayOfWeekText;
    }

    public String getWeekday(long offset) { return dateTime.plusDays(offset).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());}

}
