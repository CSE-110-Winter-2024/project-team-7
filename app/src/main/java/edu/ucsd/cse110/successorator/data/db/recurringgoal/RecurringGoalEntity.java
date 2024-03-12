package edu.ucsd.cse110.successorator.data.db.recurringgoal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

@Entity(tableName = "RecurringGoals")
public class RecurringGoalEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "recurringType")
    public int recurringType;

    @ColumnInfo(name = "startYear")
    public int startYear;

    @ColumnInfo(name = "startMonth")
    public int startMonth;

    @ColumnInfo(name = "startDayOfMonth")
    public int startDayOfMonth;

    @ColumnInfo(name = "nextYear")
    public int nextYear;

    @ColumnInfo(name = "nextMonth")
    public int nextMonth;

    @ColumnInfo(name = "nextDayOfMonth")
    public int nextDayOfMonth;

    RecurringGoalEntity(@NonNull String content, int recurringType, int startYear, int startMonth,
                        int startDayOfMonth, int nextYear, int nextMonth, int nextDayOfMonth) {
        this.content = content;
        this.recurringType = recurringType;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDayOfMonth = startDayOfMonth;
        this.nextYear = nextYear;
        this.nextMonth = nextMonth;
        this.nextDayOfMonth = nextDayOfMonth;
    }

    public static RecurringGoalEntity fromRecurringGoal(@NonNull RecurringGoal rgoal) {
        LocalDate startDate = rgoal.getStartDate();
        LocalDate nextDate = rgoal.getNextRecurringDate();
        var newGoal = new RecurringGoalEntity(rgoal.content(), rgoal.getRecurringType(),
                startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(),
                nextDate.getYear(), nextDate.getMonthValue(), nextDate.getDayOfMonth());

        newGoal.id = rgoal.id();
        return newGoal;
    }

    public @NonNull RecurringGoal toRecurringGoal() {
        return new RecurringGoal(id, content, recurringType, LocalDate.of(startYear, startMonth, startDayOfMonth),
                LocalDate.of(nextYear, nextMonth, nextDayOfMonth));
    }
}
