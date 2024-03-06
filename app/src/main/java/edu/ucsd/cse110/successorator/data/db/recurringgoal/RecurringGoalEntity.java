package edu.ucsd.cse110.successorator.data.db.recurringgoal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.data.db.standardgoal.GoalEntity;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
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

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "month")
    public int month;

    @ColumnInfo(name = "dayOfMonth")
    public int dayOfMonth;

    RecurringGoalEntity(@NonNull String content, int recurringType, int year, int month, int dayOfMonth) {
        this.content = content;
        this.recurringType = recurringType;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public static RecurringGoalEntity fromRecurringGoal(@NonNull RecurringGoal rgoal) {
        LocalDate date = rgoal.getDate();
        var newGoal = new RecurringGoalEntity(rgoal.content(), rgoal.getRecurringType(),
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        newGoal.id = rgoal.id();
        return newGoal;
    }

    public @NonNull RecurringGoal toRecurringGoal() {
        return new RecurringGoal(id, content, recurringType, LocalDate.of(year, month, dayOfMonth));
    }
}
