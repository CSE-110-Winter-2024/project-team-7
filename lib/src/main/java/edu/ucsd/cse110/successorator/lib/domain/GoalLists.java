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

    public List<Goal> getFinishedGoals() {
        return finished;
    }

    public List<Goal> getUnfinishedGoals() { return unfinished; }

    //delete one of the adds later
    public void add(Goal goal) {
        unfinished.add(goal);
        size++;
    }

    public void add(String goal) {
        unfinished.add(new Goal(goal));
        size++;
    }

    // How would we check if goal exists?
    public void finishTask(Goal goal) {
        // may need data layer to separate data storage concerns.
        var index = unfinished.indexOf(goal);
        if (index < unfinished.size()) {
            unfinished.remove(index);
            finished.add(goal);
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
