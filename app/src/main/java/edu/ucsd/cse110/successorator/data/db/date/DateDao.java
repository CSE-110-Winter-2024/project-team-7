package edu.ucsd.cse110.successorator.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface DateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DateEntity date);

    @Query("SELECT * FROM Date WHERE id = 0")
    DateEntity find();

    @Transaction
    default int replace(DateEntity newDate) {
        return Math.toIntExact(insert(newDate));
    }

    @Query("DELETE FROM Date WHERE id = 0")
    void delete();
}
