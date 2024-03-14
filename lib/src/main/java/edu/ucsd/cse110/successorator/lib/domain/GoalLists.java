package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

public interface GoalLists {
    int size();

    int unfinishedSize();

    int finishedSize();

    boolean empty();

    List<Goal> getFinishedGoals();

    List<Goal> getUnfinishedGoals();

    void add(Goal goal);

    void finishTask(Goal goal);

    void undoFinishTask(Goal goal);

    //for day update
    void clearFinished();
    void clearUnfinished();

    List<Goal> getFinishedGoalsByContext(String context);
    List<Goal> getUnfinishedGoalsByContext(String context);

    Goal get(int i);
}
