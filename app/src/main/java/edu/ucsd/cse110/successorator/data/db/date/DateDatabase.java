package edu.ucsd.cse110.successorator.data.db.date;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DateEntity.class}, version = 1)
public abstract class DateDatabase extends RoomDatabase {
    public abstract DateDao dateDao();
}
