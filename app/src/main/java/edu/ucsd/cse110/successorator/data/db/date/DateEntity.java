package edu.ucsd.cse110.successorator.data.db.date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;

@Entity(tableName = "Date")
public class DateEntity {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    public Integer id = 0;

    @ColumnInfo(name = "formattedDate")
    public String formattedDate;

    DateEntity(@NonNull String formattedDate) {
        this.formattedDate = formattedDate;
        this.id = 0;
    }

    public static DateEntity fromDate(@NonNull DateHandler date) {
        var newDate = new DateEntity(date.getFormattedDate());
        newDate.id = 0;
        return newDate;
    }
}
