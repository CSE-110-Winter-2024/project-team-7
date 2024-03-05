package edu.ucsd.cse110.successorator.data.db.date;

import edu.ucsd.cse110.successorator.data.db.date.DateDao;
import edu.ucsd.cse110.successorator.data.db.date.DateEntity;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;

public class RoomDateStorage {
    private final DateDao dateDao;

    public RoomDateStorage(DateDao dateDao) {
        this.dateDao = dateDao;
    }

    public String formattedDate() {
        return dateDao.find().formattedDate;
    }

    public void replace(DateHandler date) {
        dateDao.replace(DateEntity.fromDate(date));
    }
}
