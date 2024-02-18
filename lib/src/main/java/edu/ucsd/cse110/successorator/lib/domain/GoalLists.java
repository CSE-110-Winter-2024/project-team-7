package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

public interface GoalLists {
    int size();

    //might be necessary for crossing out
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

    Goal get(int i);
}
