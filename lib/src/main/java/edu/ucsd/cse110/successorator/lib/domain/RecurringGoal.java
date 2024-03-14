package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import java.io.Serializable;

public class RecurringGoal implements Serializable {

    public static final int DAILY = 0, WEEKLY = 1, MONTHLY = 2, YEARLY = 3;

    private @Nullable Integer id;
    private final @NonNull String content;
    private final int recurringType;
    private final @NonNull String context;
    private final LocalDate startDate;
    private LocalDate nextRecurringDate;
    private DayOfWeek dayOfWeek;
    private int weekOfMonth;

    public RecurringGoal(@Nullable Integer id, @NonNull String content, int recurringType,
                         LocalDate startDate, @NonNull String context) {
        this.id = id;
        this.content = content;
        this.recurringType = recurringType;
        this.startDate = startDate;
        this.dayOfWeek = startDate.getDayOfWeek();
        this.weekOfMonth = ((startDate.getDayOfMonth()-1) / 7) + 1;
        findNextRecurringDate(startDate);
        this.context = context;
    }

    public RecurringGoal(@Nullable Integer id, @NonNull String content, int recurringType,
                         LocalDate startDate, LocalDate nextRecurringDate, @NonNull String context) {
        this.id = id;
        this.content = content;
        this.recurringType = recurringType;
        this.context = context;
        this.startDate = startDate;
        this.dayOfWeek = startDate.getDayOfWeek();
        this.weekOfMonth = ((startDate.getDayOfMonth()-1) / 7) + 1;
        this.nextRecurringDate = nextRecurringDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getNextRecurringDate() {
        return nextRecurringDate;
    }

    public @NonNull String getContext() {
        return context;
    }

    public boolean recurToday(LocalDate today) {
        if(today.isEqual(nextRecurringDate) || today.isAfter(nextRecurringDate)) {
            findNextRecurringDate(today);
            return true;
        }
        return false;
    }

    public void findNextRecurringDate(LocalDate today) {
        if(recurringType == DAILY) {
            nextRecurringDate = today.plusDays(1);
        } else if(recurringType == WEEKLY) {
            nextRecurringDate = today.with(TemporalAdjusters.next(dayOfWeek));
            if(nextRecurringDate.isEqual(today)) {
                nextRecurringDate = today.plusWeeks(1);
            }
        } else if(recurringType == MONTHLY) {
            LocalDate prevMonth = today.plusMonths(-1);
            LocalDate nextMonth = today.plusMonths(1);


            LocalDate targetDate = prevMonth.with(TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, dayOfWeek));
            if(targetDate.isAfter(today)) {
                nextRecurringDate = targetDate;
                return;
            }
            targetDate = today.with(TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, dayOfWeek));
            if(targetDate.isAfter(today)) {
                nextRecurringDate = targetDate;
                return;
            }

            targetDate = nextMonth.with(TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, dayOfWeek));
            nextRecurringDate = targetDate;
        } else if(recurringType == YEARLY) {
            nextRecurringDate = startDate.withYear(today.getYear());
            if(nextRecurringDate.isBefore(today) || nextRecurringDate.isEqual(today)) {
                nextRecurringDate = nextRecurringDate.plusYears(1);
            }
        }
    }

    public Goal toGoal() {
        return new Goal(null, content, false, true, context);
    }

    public String toString() {
        return content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
