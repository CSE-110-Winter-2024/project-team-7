package edu.ucsd.cse110.successorator.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GoalEntity.class}, version = 1)
public abstract class GoalDatabase extends RoomDatabase{
    public abstract GoalDao goalDao();
}
