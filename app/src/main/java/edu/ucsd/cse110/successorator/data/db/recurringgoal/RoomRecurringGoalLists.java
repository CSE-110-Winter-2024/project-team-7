package edu.ucsd.cse110.successorator.data.db.recurringgoal;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalLists;


public class RoomRecurringGoalLists implements RecurringGoalLists {

    private final RecurringGoalDao rgoalDao;

    public RoomRecurringGoalLists(RecurringGoalDao rgoalDao) {
        this.rgoalDao = rgoalDao;
    }
    public int size() {
        return rgoalDao.count();
    }

    public boolean empty() {
        return rgoalDao.count() == 0;
    }

    public List<RecurringGoal> getRecurringGoals() {
        List<RecurringGoal> nonEntityGoals = new ArrayList<RecurringGoal>();
        List<RecurringGoalEntity> entityGoals = rgoalDao.findAll();

        for(int i = 0; i < entityGoals.size(); i++) {
            nonEntityGoals.add(entityGoals.get(i).toRecurringGoal());
        }
        //temp

        return nonEntityGoals;
    }

    public void add(RecurringGoal rgoal) {
        rgoalDao.insert(RecurringGoalEntity.fromRecurringGoal(rgoal));
    }

    public void delete(RecurringGoal rgoal) {
        rgoalDao.delete(rgoal.id());
    }
}
