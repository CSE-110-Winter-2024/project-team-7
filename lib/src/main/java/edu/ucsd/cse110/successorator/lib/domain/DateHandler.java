package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class DateHandler implements Subject {

    private String formattedDate;
    private LocalDateTime dateTime;
    private DateTimeFormatter dateFormat;
    private final List<Observer> observers = new ArrayList<>();

    public DateHandler() {
        this.dateFormat = DateTimeFormatter.ofPattern("MM/dd");
        this.dateTime = null;
        updateDate((String) null);
    }

    public void updateDate(String previousDate) {
        this.dateTime = LocalDateTime.now();
        LocalTime currentTime = dateTime.toLocalTime();
        if (currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.of(2, 0))) {
            dateTime = dateTime.minusDays(1);
        }
        String dayOfWeek = dateTime.getDayOfWeek().name();
        String dateString = dateFormat.format(dateTime);
        String newFormattedDate = dayOfWeek + " " + dateString;
        System.out.println(formattedDate);
        if (formattedDate == null || !(formattedDate.equals(newFormattedDate)) || !(previousDate.equals(newFormattedDate))) {
            this.formattedDate = newFormattedDate;
            notifyObservers();
        }
    }

    public void updateDate(LocalDateTime dateInput) {
        this.dateTime = dateInput;
        String dayOfWeek = dateTime.getDayOfWeek().name();
        String dateString = dateFormat.format(dateTime);
        String newFormattedDate = dayOfWeek + " " + dateString;
        if (formattedDate == null || !(formattedDate.equals(newFormattedDate))) {
            this.formattedDate = newFormattedDate;
            notifyObservers();
        }
    }

    public void skipDay() {
        this.dateTime = dateTime.plusDays(1);
        updateDate(dateTime);
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    @Nullable
    @Override
    public Object getValue() {
        return formattedDate;
    }

    @Override
    public void observe(Observer observer) {
        observers.add(observer);
        observer.onChanged(formattedDate);
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

    public String getObservers() {
        return observers.toString();
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    public String monthDay() {
        return dateFormat.format(dateTime);
    }

}
