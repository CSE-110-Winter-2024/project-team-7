package edu.ucsd.cse110.successorator.data.db.standardgoal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

@Entity(tableName = "Goals")
public class GoalEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "finished")
    public boolean finished;

    GoalEntity(@NonNull String content, boolean finished) {
        this.content = content;
        this.finished = finished;
    }

    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var newGoal = new GoalEntity(goal.content(), goal.getFinished());
        newGoal.id = goal.id();
        return newGoal;
    }

    public @NonNull Goal toGoal() {
        return new Goal(id, content, finished);
    }
}
