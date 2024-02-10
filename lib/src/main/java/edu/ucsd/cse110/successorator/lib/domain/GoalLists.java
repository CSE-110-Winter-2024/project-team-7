package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;
import java.util.ArrayList;
public class GoalLists {
    private List<Goal> unfinished;
    private List<Goal> finished;
    private int size;

    public GoalLists() {
        unfinished = new ArrayList<Goal>();
        finished = new ArrayList<Goal>();
        size = 0;
    }

    public GoalLists(List<Goal> unfinished, List<Goal> finished) {
        this.unfinished = unfinished;
        this.finished = finished;
        size = unfinished.size() + finished.size();
    }

    public int size() {
        return size;
    }

    //might be necessary for crossing out
    public int unfinishedSize() {
        return unfinished.size();
    }

    public boolean empty() {
        return size == 0;
    }

    public Goal get(int index) {
        if(index >= unfinished.size())
            return finished.get(index - unfinished.size());
        return unfinished.get(index);
    }

    //delete one of the adds later
    public void add(Goal goal) {
        unfinished.add(goal);
        size++;
    }

    public void add(String goal) {
        unfinished.add(new Goal(goal));
        size++;
    }

    public void finishTask(int index) {
        // may need data layer to separate data storage concerns.
        if (index < unfinished.size()) {
            var removedTask = unfinished.remove(index);
            finished.add(removedTask);
        }

    }

    //for day update
    public void clearFinished() {
        finished.clear();
        size = unfinished.size();
    }

    //for testing
    public String toString() {
        String retval = "unfinished: \n";
        for(int i = 0; i < size; i++) {
            if(i == unfinishedSize())
                retval += "finished: \n";
            retval += i + get(i).toString() + "\n";
        }
        return retval;
    }
}
