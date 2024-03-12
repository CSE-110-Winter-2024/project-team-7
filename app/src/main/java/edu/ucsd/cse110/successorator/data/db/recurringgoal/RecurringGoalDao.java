package edu.ucsd.cse110.successorator.data.db.recurringgoal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface RecurringGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecurringGoalEntity goal);

    @Query("SELECT * FROM RecurringGoals WHERE id = :id")
    RecurringGoalEntity find(int id);

    @Query("SELECT * FROM RecurringGoals")
    List<RecurringGoalEntity> findAll();

    @Query("SELECT COUNT(*) FROM RecurringGoals")
    int count();

    @Transaction
    default int add(RecurringGoalEntity goal) {
        var newGoal = new RecurringGoalEntity(goal.content, goal.recurringType,
                    goal.startYear, goal.startMonth, goal.startDayOfMonth,
                    goal.nextYear, goal.nextMonth, goal.nextDayOfMonth);
        return Math.toIntExact(insert(newGoal));
    }

    @Query("DELETE FROM RecurringGoals WHERE id = :id")
    void delete(int id);
}
