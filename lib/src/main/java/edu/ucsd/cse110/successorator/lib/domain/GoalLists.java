package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

public interface GoalLists {
    int size();

    //might be necessary for crossing out
    int unfinishedSize();

    int FinishedSize();

    boolean empty();

    Goal get(int index);

    List<Goal> getFinishedGoals();

    List<Goal> getUnfinishedGoals();

    //delete one of the adds later
    void add(Goal goal);

    // How would we check if goal exists?
    void finishTask(Goal goal);

    void undoFinishTask(Goal goal);

    //for day update
    void clearFinished();
}
