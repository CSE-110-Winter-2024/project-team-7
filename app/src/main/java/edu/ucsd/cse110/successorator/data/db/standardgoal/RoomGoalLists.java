package edu.ucsd.cse110.successorator.data.db.standardgoal;

import java.util.ArrayList;
import java.util.List;

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
        goalDao.updateFinishedStatus(goal.id(), true);
    }

    @Override
    public void undoFinishTask(Goal goal) {
        goalDao.updateFinishedStatus(goal.id(), false);
    }

    @Override
    public void clearFinished() {
        List<GoalEntity> finished = goalDao.findAllFinished();
        for(int i = 0; i < finished.size(); i++) {
            goalDao.delete(finished.get(i).id);
        }
    }

    @Override
    public void clearUnfinished() {
        List<GoalEntity> unfinished = goalDao.findAllUnfinished();
        for(int i = 0; i < unfinished.size(); i++) {
            goalDao.delete(unfinished.get(i).id);
        }
    }

    @Override
    public Goal get(int index) {
        List<Goal> unfinished = getUnfinishedGoals();
        List<Goal> finished = getFinishedGoals();

        if(index >= unfinished.size())
            return finished.get(index - unfinished.size());
        return unfinished.get(index);
    }

    public List<Goal> getUnfinishedGoalsByContext(String context) {
        List<Goal> goalsByContext = new ArrayList<>();
        List<GoalEntity> goalEntities = goalDao.findUnfinishedByContext(context);
        for (GoalEntity entity : goalEntities) {
            goalsByContext.add(entity.toGoal());
        }
        return goalsByContext;
    }

    public List<Goal> getFinishedGoalsByContext(String context) {
        List<Goal> goalsByContext = new ArrayList<>();
        List<GoalEntity> goalEntities = goalDao.findFinishedByContext(context);
        for (GoalEntity entity : goalEntities) {
            goalsByContext.add(entity.toGoal());
        }
        return goalsByContext;
    }
}
