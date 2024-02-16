package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalLists;

public class RoomGoalLists implements GoalLists {

    private final GoalDao goalDao;

    public RoomGoalLists(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    @Override
    public int size() {
        return goalDao.count();
    }

    //might be necessary for crossing out
    @Override
    public int unfinishedSize() {
        return goalDao.unfinishedCount();
    }

    @Override
    public int finishedSize() {
        return goalDao.finishedCount();
    }

    @Override
    public boolean empty() {
        return goalDao.count() == 0;
    }

    @Override
    public List<Goal> getFinishedGoals() {

        List<Goal> nonEntityFinished = new ArrayList<Goal>();
        List<GoalEntity> fromDao = goalDao.findAllFinished();
        for(int i = 0; i < fromDao.size(); i++) {
            nonEntityFinished.add(fromDao.get(i).toGoal());
        }

        return nonEntityFinished;
    }

    @Override
    public List<Goal> getUnfinishedGoals() {

        List<Goal> nonEntityUnfinished = new ArrayList<Goal>();
        List<GoalEntity> fromDao = goalDao.findAllUnfinished();
        for(int i = 0; i < fromDao.size(); i++) {
            nonEntityUnfinished.add(fromDao.get(i).toGoal());
        }

        return nonEntityUnfinished;
    }

    @Override
    public void add(Goal goal) {
        goalDao.insert(GoalEntity.fromGoal(goal));
    }

    @Override
    public void finishTask(Goal goal) {
        // may need data layer to separate data storage concerns.
        goalDao.finish(goal.id());
    }

    @Override
    public void undoFinishTask(Goal goal) {
        // may need data layer to separate data storage concerns.
        goalDao.unfinish(goal.id());
    }

    @Override
    public void clearFinished() {
        List<GoalEntity> finished = goalDao.findAllFinished();
        for(int i = 0; i < finished.size(); i++) {
            goalDao.delete(finished.get(i).id);
        }
    }




}
