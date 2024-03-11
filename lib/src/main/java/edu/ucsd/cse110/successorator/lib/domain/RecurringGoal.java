package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import java.io.Serializable;
import java.util.Objects;

public class RecurringGoal implements Serializable {

    public static final int DAILY = 0, WEEKLY = 1, MONTHLY = 2, YEARLY = 3;

    private final @Nullable Integer id;
    private final @NonNull String content;
    private final int recurringType;
    private final LocalDate date;
    private final @NonNull String context;
    private DayOfWeek dayOfWeek;
    private int weekOfMonth;

    public RecurringGoal(@Nullable Integer id, @NonNull String content, int recurringType,
                         LocalDate date, @NonNull String context) {
        this.id = id;
        this.content = content;
        this.recurringType = recurringType;
        this.date = date;
        this.context = context;
        this.dayOfWeek = date.getDayOfWeek();
        this.weekOfMonth = ((date.getDayOfMonth()-1) / 7) + 1;
    }

    public @Nullable Integer id() {
        return id;
    }

    public @NonNull String content() {
        return content;
    }

    public int getRecurringType() {
        return recurringType;
    }

    public LocalDate getDate() {
        return date;
    }

    public @NonNull String getContext() {
        return context;
    }

    public Goal toGoal() {
        return new Goal(null, content, false, true, context);
    }

    public boolean recurToday(LocalDate today) {
        if(recurringType == DAILY) {
            return true;
        }
        else if(recurringType == WEEKLY) {
            if(today.getDayOfWeek().getValue() == date.getDayOfWeek().getValue()) {
                return true;
            }
        }
        else if(recurringType == MONTHLY) {
            System.out.println(dayOfWeek + "     " + weekOfMonth);
            LocalDate prevMonth;
            if(today.getMonthValue() == 1) {
                prevMonth = today.withMonth(12);
                prevMonth = prevMonth.withYear(prevMonth.getYear()-1);
            }
            else {
                prevMonth = today.withMonth(today.getMonthValue()-1);
            }

            LocalDate targetDate = prevMonth.with(TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, dayOfWeek));
            System.out.println(targetDate);
            if(targetDate.isEqual(today)) {
                return true;
            }

            targetDate = today.with(TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, dayOfWeek));
            System.out.println(targetDate);
            if(targetDate.isEqual(today)) {
                return true;
            }

        }
        else { //recurringType == YEARLY
            if(today.getMonthValue() == date.getMonthValue() &&
                    today.getDayOfMonth() == date.getDayOfMonth()) {
                return true;
            }
        }
        return false;
    }

}
