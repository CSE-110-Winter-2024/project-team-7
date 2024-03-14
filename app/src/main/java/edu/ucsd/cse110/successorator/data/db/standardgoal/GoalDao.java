package edu.ucsd.cse110.successorator.data.db.standardgoal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GoalEntity goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<GoalEntity> goals);

    @Query("SELECT * FROM Goals WHERE id = :id")
    GoalEntity find(int id);

    @Query("SELECT * FROM Goals WHERE finished = false")
    List<GoalEntity> findAllUnfinished();

    @Query("SELECT * FROM Goals WHERE finished = true")
    List<GoalEntity> findAllFinished();

    @Query("SELECT * FROM Goals WHERE id = :id")
    LiveData<GoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM Goals WHERE finished = false")
    LiveData<List<GoalEntity>> findAllUnfinishedAsLiveData();

    @Query("SELECT * FROM Goals WHERE finished = true")
    LiveData<List<GoalEntity>> findAllFinishedAsLiveData();

    @Query("SELECT * FROM Goals WHERE context = :context AND finished = false")
    List<GoalEntity> findUnfinishedByContext(String context);

    @Query("SELECT * FROM Goals WHERE context = :context AND finished = true")
    List<GoalEntity> findFinishedByContext(String context);

    @Query("SELECT COUNT(*) FROM Goals")
    int count();

    @Query("SELECT COUNT(*) FROM Goals WHERE finished = false")
    int unfinishedCount();

    @Query("SELECT COUNT(*) FROM Goals WHERE finished = true")
    int finishedCount();

    @Transaction
    default int add(GoalEntity goal) {
        var newGoal = new GoalEntity(goal.content, goal.finished, goal.fromRecurring, goal.context);
        return Math.toIntExact(insert(newGoal));
    }

    @Query("UPDATE Goals SET finished = :finished WHERE id = :id")
    void updateFinishedStatus(int id, boolean finished);

    @Transaction
    default int unfinish(int id) {
        var unfinishedGoal = find(id);
        var newlyUnfinishedGoal = new GoalEntity(unfinishedGoal.content, false, unfinishedGoal.fromRecurring, unfinishedGoal.context);
        delete(id);
        return Math.toIntExact(insert(newlyUnfinishedGoal));
    }

    @Query("DELETE FROM Goals WHERE id = :id")
    void delete(int id);

}
