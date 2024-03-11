package edu.ucsd.cse110.successorator.data.db.recurringgoal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface RecurringGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecurringGoalEntity goal);

    @Query("SELECT * FROM RecurringGoals WHERE id = :id")
    RecurringGoalEntity find(int id);

    @Query("SELECT * FROM RecurringGoals")
    List<RecurringGoalEntity> findAll();

    @Query("SELECT * FROM RecurringGoals WHERE context = :context")
    List<RecurringGoalEntity> findByContext(String context);

    @Query("SELECT COUNT(*) FROM RecurringGoals")
    int count();

    @Transaction
    default int add(RecurringGoalEntity goal) {
        var newGoal = new RecurringGoalEntity(goal.content, goal.recurringType,
                goal.year, goal.month, goal.dayOfMonth, goal.context);
        return Math.toIntExact(insert(newGoal));
    }

    @Query("DELETE FROM RecurringGoals WHERE id = :id")
    void delete(int id);
}
