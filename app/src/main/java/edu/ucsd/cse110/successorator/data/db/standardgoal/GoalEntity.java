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

    @ColumnInfo(name = "fromRecurring")
    public boolean fromRecurring;

    @ColumnInfo(name = "context")
    public String context;

    GoalEntity(@NonNull String content, boolean finished, boolean fromRecurring, @NonNull String context) {
        this.content = content;
        this.finished = finished;
        this.fromRecurring = fromRecurring;
        this.context = context;
    }

    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var newGoal = new GoalEntity(goal.content(), goal.finished(), goal.isFromRecurring(), goal.getContext());
        newGoal.id = goal.id();
        return newGoal;
    }

    public @NonNull Goal toGoal() {
        return new Goal(id, content, finished, fromRecurring, context);
    }
}
